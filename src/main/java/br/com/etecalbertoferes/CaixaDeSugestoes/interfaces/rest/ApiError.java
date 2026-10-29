package br.com.etecalbertoferes.CaixaDeSugestoes.interfaces.rest;

import java.time.Instant;
import java.util.Map;
public record ApiError(Instant timestamp, int status, String message, String path, Map<String, String> fieldErrors) {}
