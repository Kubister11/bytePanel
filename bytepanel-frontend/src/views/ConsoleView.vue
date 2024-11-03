<script setup>
import SideBar from "@/components/SideBar.vue";
import Console from "@/components/console/Console.vue";
import ServerPerformance from "@/components/console/ServerPerformance.vue";
</script>

<template>
  <main class="flex w-full h-full">
    <SideBar />
    <div class="w-full h-full flex gap-3">
      <Console />
      <ServerPerformance />
    </div>
  </main>
</template>
