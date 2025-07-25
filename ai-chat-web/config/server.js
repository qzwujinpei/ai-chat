export default {
  allowedHosts: [
    '*.cpolar.top', // 允许所有 cpolar.top 的子域名
    'localhost',
    'host.docker.internal',
  ],
  proxy: {
    '/api': {
      target: 'http://host.docker.internal:8080',
      changeOrigin: true,
      rewrite: (path) => path.replace(/^\/api/, ''),
    },
  },
}
