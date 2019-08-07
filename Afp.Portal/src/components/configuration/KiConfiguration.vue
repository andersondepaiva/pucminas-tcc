<template>
  <div>
    <h1 class="ki-title">Configurations</h1>
    <v-tabs icons-and-text>
      <v-tabs-slider></v-tabs-slider>
      <v-tab
        ripple
        href="#tab-katalon"
      >
        <v-badge>
          <v-icon class="ki-icon-title-tab">widgets</v-icon> Katalon
        </v-badge>
      </v-tab>
      <v-tab
        ripple
        href="#tab-projects"
      >
        <v-badge>
          <v-icon class="ki-icon-title-tab">folder_open</v-icon> Projects
        </v-badge>
      </v-tab>
      <v-tab
        ripple
        href="#tab-users"
      >
        <v-badge>
          <v-icon class="ki-icon-title-tab">people</v-icon> Users
        </v-badge>
      </v-tab>
      <v-tab-item :value="'tab-katalon'">
        <v-card flat>
          <ki-katalon-studio></ki-katalon-studio>
        </v-card>
      </v-tab-item>
      <v-tab-item :value="'tab-projects'">
        <v-card flat>
          <ki-projects-list></ki-projects-list>
        </v-card>
      </v-tab-item>
      <v-tab-item :value="'tab-users'">
        <v-card flat>
          <ki-user-list></ki-user-list>
        </v-card>
      </v-tab-item>
    </v-tabs>
  </div>
</template>

<script>
import KiProjectsList from './projects/KiProjectsList.vue'
import KiKatalonStudio from './katalon-studio/KiKatalonStudio.vue'
import KiUserList from './user/KiUserList.vue'

export default {
  components: {
    'ki-projects-list': KiProjectsList,
    'ki-katalon-studio': KiKatalonStudio,
    'ki-user-list': KiUserList
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
