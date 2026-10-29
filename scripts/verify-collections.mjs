// Executa os requests e scripts exportados contra uma API local; sem dependências npm.
// Uso: node scripts/verify-collections.mjs [http://localhost:8000]
import { readFile } from 'node:fs/promises';
import { strict as assert } from 'node:assert';
import { runInNewContext } from 'node:vm';
const root = new URL('../collections/', import.meta.url);
function expect(actual) {
  const chain = {
    eql(expected) { if (typeof expected === "object") assert.equal(JSON.stringify(actual), JSON.stringify(expected));
      else assert.equal(actual, expected); },
    a(type) { assert.equal(typeof actual, type); },
    an(type) { if (type === 'array') assert.ok(Array.isArray(actual)); else assert.equal(typeof actual, type); },
    property(key) { assert.ok(actual != null && Object.hasOwn(actual, key), `Propriedade ausente: ${key}`); }
  };
  for (const key of ['to', 'be', 'have']) chain[key] = chain;
  return chain;
}
for (const format of ['postman', 'insomnia']) {
  const file = format === 'postman' ? 'caixa-de-sugestoes.postman_collection.json' : 'caixa-de-sugestoes.insomnia.json';
  const collection = JSON.parse(await readFile(new URL(file, root), 'utf8'));
  const vars = format === 'postman' ? Object.fromEntries(collection.variable.map(v => [v.key, v.value])) : { ...collection.resources.find(r => r._type === 'environment').data };
  if (process.env.ADMIN_USERNAME) vars.adminUsername = process.env.ADMIN_USERNAME;
  if (process.env.ADMIN_PASSWORD) vars.adminPassword = process.env.ADMIN_PASSWORD;
  if (process.argv[2]) vars.baseUrl = process.argv[2];
  const substitute = value => value.replace(/{{\s*(?:_\.)?(\w+)\s*}}/g, (_, key) => {
    assert.notEqual(vars[key], undefined, `Variável ausente: ${key}`);
    return String(vars[key]);
  });
  const requests = format === 'postman' ? collection.item : collection.resources.filter(r => r._type === 'request');
  for (const item of requests) {
    const variables = { get: key => vars[key], set: (key, value) => { vars[key] = value; } };
    const pre = format === 'postman' ? item.event.find(e => e.listen === 'prerequest')?.script.exec.join('\n') : item.preRequestScript;
    if (pre) runInNewContext(pre, { pm: { variables }, insomnia: { environment: variables } }, { timeout: 1000 });
    const request = format === 'postman' ? item.request : item;
    const url = new URL(substitute(request.url));
    if (format === 'insomnia') for (const p of request.parameters) if (!p.disabled) url.searchParams.append(p.name, substitute(p.value));
    const headers = Object.fromEntries((request.header ?? request.headers).map(h => [h.key ?? h.name, substitute(h.value)]));
    const auth = request.auth ?? request.authentication;
    if (auth?.type === 'basic') {
      const username = format === 'postman' ? auth.basic.find(x => x.key === 'username').value : auth.username;
      const password = format === 'postman' ? auth.basic.find(x => x.key === 'password').value : auth.password;
      headers.Authorization = 'Basic ' + Buffer.from(substitute(username) + ':' + substitute(password)).toString('base64');
    }
    const rawBody = format === 'postman' ? request.body?.raw : request.body?.text;
    const response = await fetch(url, { method: request.method, headers, body: rawBody ? substitute(rawBody) : undefined });
    const text = await response.text();
    const test = (name, callback) => { try { callback(); } catch (error) { throw new Error(`${format}: ${item.name}: ${name}: ${error.message}`, { cause: error }); } };
    const script = format === 'postman' ? item.event.find(e => e.listen === 'test').script.exec.join('\n') : request.afterResponseScript;
    runInNewContext(script, {
      pm: { test, expect, variables, collectionVariables: variables, response: {
        json: () => JSON.parse(text), text: () => text, to: { have: { status: code => assert.equal(response.status, code) } }
      } },
      insomnia: { test, expect, environment: variables, response: { code: response.status, json: () => JSON.parse(text), text: () => text } }
    }, { timeout: 1000 });
    console.log(`${format}: ${response.status} ${item.name}`);
  }
}
