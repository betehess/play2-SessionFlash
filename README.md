play2-SessionFlash
==================

Small Play2 projects to demo an issue when using Session/Flash scopes with application.context

This bug appeared recently, so this requires to run against the latest version of Play on github.

You can't just run `test` (different applications must run with a different `application.context`):

```bash
../Play20/play 'test-only test.SlashContext' 'test-only test.FooContext'
```