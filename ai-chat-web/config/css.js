export default {
  preprocessorOptions: {
    scss: {
      javascriptEnabled: true,
      additionalData: `@use "@/styles/variable.scss" as *;`,
    },
  },
}
