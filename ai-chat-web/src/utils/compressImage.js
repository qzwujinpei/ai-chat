export const compressImage = (file, maxWidth = 800, maxHeight = 800, quality = 0.9) => {
  return new Promise((resolve, reject) => {
    const reader = new FileReader()
    reader.readAsDataURL(file)

    reader.onloadend = () => {
      const img = new Image()
      img.src = reader.result

      img.onload = () => {
        const canvas = document.createElement('canvas')
        const ctx = canvas.getContext('2d')

        let width = img.width
        let height = img.height

        // 计算新的宽高以保持比例
        if (width > height) {
          if (width > maxWidth) {
            height = Math.round((height *= maxWidth / width))
            width = maxWidth
          }
        } else {
          if (height > maxHeight) {
            width = Math.round((width *= maxHeight / height))
            height = maxHeight
          }
        }
        canvas.width = width
        canvas.height = height

        // 绘制缩小后的图片到 canvas 上
        ctx.drawImage(img, 0, 0, width, height)

        // 将 canvas 内容转换为 blob 并 resolve
        canvas.toBlob(
          (blob) => {
            if (blob) {
              resolve(new File([blob], file.name, { type: 'image/jpeg', lastModified: Date.now() }))
            } else {
              reject('压缩图片时出错')
            }
          },
          'image/jpeg',
          quality,
        )
      }

      img.onerror = () => {
        reject('加载图片时出错')
      }
    }

    reader.onerror = () => {
      reject('读取文件时出错')
    }
  })
}
