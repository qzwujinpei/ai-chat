import request from '@/utils/request.js'

// 获取验证码
export const getCaptchaService = () => {
  return request({
    url: '/captcha',
    method: 'get',
  })
}
