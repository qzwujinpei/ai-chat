import { defineConfig } from 'vite'
import pluginsConfig from './config/plugins'
import serverConfig from './config/server'
import resolveConfig from './config/resolve'
import cssConfig from './config/css'

export default defineConfig({
  plugins: pluginsConfig,
  server: serverConfig,
  resolve: resolveConfig,
  css: cssConfig,
})
