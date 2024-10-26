<script setup>
import Console from "@/components/Console.vue";
import SideBar from "@/components/SideBar.vue";
import ServerPerformance from "@/components/ServerPerformance.vue";
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
