import { defineStore } from 'pinia'

export const useUserStore = defineStore('user', {
  state: () => ({
    token: localStorage.getItem('jxc_token') || '',
    userInfo: JSON.parse(localStorage.getItem('jxc_user') || '{}'),
    menus: JSON.parse(localStorage.getItem('jxc_menus') || '[]')
  }),
  actions: {
    setLogin(data) {
      this.token = data.token
      this.userInfo = data.userInfo
      this.menus = data.userInfo.menus || []
      localStorage.setItem('jxc_token', this.token)
      localStorage.setItem('jxc_user', JSON.stringify(this.userInfo))
      localStorage.setItem('jxc_menus', JSON.stringify(this.menus))
    },
    logout() {
      this.token = ''
      this.userInfo = {}
      this.menus = []
      localStorage.removeItem('jxc_token')
      localStorage.removeItem('jxc_user')
      localStorage.removeItem('jxc_menus')
    }
  }
})
