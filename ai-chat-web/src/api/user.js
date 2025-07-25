import request from '@/utils/request'

// resetPassword
export const resetPassword = () => {
  return request({
    url: '/api/user/resetPassword',
    method: 'post',
  })
}
export const sendCode = () => {
  return request({
    url: '/api/user/resetPassword',
    method: 'post',
  })
}
export const userLoginService = (data) => {
  return request({
    url: '/user/login',
    method: 'post',
    data,
  })
}
export const userRegisterService = (data) => {
  return request({
    url: '/user/register',
    method: 'post',
    data,
  })
}
