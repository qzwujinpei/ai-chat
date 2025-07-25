import { fileURLToPath, URL } from 'node:url'

export default {
  alias: {
    '@': fileURLToPath(new URL('../src', import.meta.url)),
  },
}
