package br.com.etecalbertoferes.CaixaDeSugestoes.infrastructure.security;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.PosixFilePermissions;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Set;

public final class JwtKeyStore {
    private JwtKeyStore() {}
    public static synchronized String loadOrCreate(Path path) {
        try {
            Path absolute = path.toAbsolutePath();
            boolean posix = absolute.getFileSystem().supportedFileAttributeViews().contains("posix");
            if (posix) Files.createDirectories(absolute.getParent(), PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString("rwx------")));
            else Files.createDirectories(absolute.getParent());
            var options = Set.of(StandardOpenOption.CREATE, StandardOpenOption.READ, StandardOpenOption.WRITE);
            try (FileChannel channel = posix
                    ? FileChannel.open(absolute, options, PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString("rw-------")))
                    : FileChannel.open(absolute, options);
                 var lock = channel.lock()) {
                if (channel.size() == 0) {
                    byte[] bytes = new byte[32]; new SecureRandom().nextBytes(bytes);
                    byte[] text = Base64.getEncoder().encode(bytes);
                    ByteBuffer buffer = ByteBuffer.wrap(text);
                    while (buffer.hasRemaining()) channel.write(buffer);
                    channel.force(true);
                }
                if (channel.size() > 8192) throw new IllegalStateException("Arquivo de chave JWT inválido");
                channel.position(0);
                ByteBuffer buffer = ByteBuffer.allocate((int) channel.size());
                while (buffer.hasRemaining() && channel.read(buffer) != -1) { }
                return new String(buffer.array(), StandardCharsets.US_ASCII).strip();
            }
        } catch (IOException ex) {
            throw new IllegalStateException("Não foi possível ler ou gravar a chave JWT; configure JWT_KEY_FILE em uma pasta gravável", ex);
        }
    }
}
