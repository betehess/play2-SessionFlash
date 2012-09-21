play2-SessionFlash
==================

Small Play2 project to demo an issue when using Session/Flash scopes with `application.context`.

This bug appeared recently, so this requires to run against the latest version of Play on github.

You can't just run `play test` (different applications must run with a different `application.context`):

```bash
play 'test-only test.SlashContext' 'test-only test.FooContext'
```