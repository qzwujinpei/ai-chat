/**
 * 格式化时间
 * @param {Date|string|number} - 时间戳或日期对象
 * @returns {string} 格式化后的时间字符串
 */
export function formatTime() {
  const date = new Date()
  const hours = date.getHours().toString().padStart(2, '0')
  const minutes = date.getMinutes().toString().padStart(2, '0')

  return `${hours}:${minutes}`
}
