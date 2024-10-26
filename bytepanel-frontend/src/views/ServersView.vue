<script setup lang="ts">
import ServersStatistics from "@/components/ServersStatistics.vue";
import ServerList from "@/components/ServerList.vue";
</script>

<template>
  <main class="flex w-full h-full">
    <div class="h-full w-5/6 flex flex-col">
      <ServerList />
    </div>
    <div class="h-full w-1/6 flex flex-col gap-5">
      <ServersStatistics />
    </div>
  </main>
</template>