/**
 * 事件发射器类，用于管理事件的监听和触发
 */
class EventEmitter {
  /**
   * 构造函数，初始化事件监听器集合
   */
  constructor() {
    // 存储事件名称及其对应的监听器集合
    this.listeners = {
      // 认证失败事件的监听器集合
      'API:UN_AUTH': new Set(),
      // API 无效事件的监听器集合
      'API:INVALID': new Set(),
      // 路由改变事件的监听器集合
      ROUTE_CHANGE: new Set(),
    }
  }

  /**
   * 为指定事件添加监听器
   * @param {string} eventName - 要监听的事件名称
   * @param {Function} listener - 事件触发时要执行的回调函数
   */
  on(eventName, listener) {
    // 检查事件名称是否存在于监听器对象中
    if (this.listeners[eventName]) {
      // 将新的监听器添加到对应的事件监听器集合中
      this.listeners[eventName].add(listener)
    } else {
      // 若事件名称未知，在控制台输出警告信息
      console.warn(`Unknown event name: ${eventName}`)
    }
  }

  /**
   * 触发指定事件，并将参数传递给所有监听器
   * @param {string} eventName - 要触发的事件名称
   * @param {...any} args - 传递给监听器的参数
   */
  emit(eventName, ...args) {
    // 检查事件名称是否存在于监听器对象中
    if (this.listeners[eventName]) {
      // 遍历事件对应的监听器集合，并依次调用每个监听器，传递参数
      this.listeners[eventName].forEach((listener) => listener(...args))
    }
  }
}
export default new EventEmitter()
