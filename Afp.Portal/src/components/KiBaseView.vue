<template>
  <v-app>
    <ki-menu />
    <v-content style="padding: 0px 0px 0px 0px;">
      <v-container fluid>
        <transition name="fade">
          <router-view></router-view>
        </transition>
      </v-container>
    </v-content>
  </v-app>
</template>

<script>
import KiMenu from '../shared/menu/KiMenu.vue'

export default {
  components: {
    'ki-menu': KiMenu
  }
}
</script>

<style>
</style>
