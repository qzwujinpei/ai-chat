<script setup>
import { useAiStore } from '@/stores'
const { selectedFiles } = storeToRefs(useAiStore())
// 移除文件
const removeFile = (index) => {
  selectedFiles.value = selectedFiles.value.filter((_, i) => i !== index)
}
const formatFileSize = (bytes) => {
  if (bytes < 1024) return bytes + ' B'
  if (bytes < 1024 * 1024) return (bytes / 1024).toFixed(1) + ' KB'
  return (bytes / (1024 * 1024)).toFixed(1) + ' MB'
}
</script>

<template>
  <div v-for="(file, index) in selectedFiles" :key="index" class="file-item">
    <div class="file-info">
      <span class="file-name">{{ file.name }}</span>
      <span class="file-size">({{ formatFileSize(file.size) }})</span>
    </div>
    <button class="remove-btn" @click="removeFile(index)"></button>
  </div>
</template>

<style scoped lang="scss">
.file-item {
  --accent-color: #2563eb;
  --error-color: #dc2626;
  --success-color: #16a34a;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 1rem 1.5rem;
  background: white;
  border-radius: 16px;
  margin-bottom: 0.75rem;
  border: 1px solid #e2e8f0;
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
  box-shadow:
    0 1px 3px rgba(0, 0, 0, 0.04),
    0 2px 6px rgba(0, 0, 0, 0.04);

  &:hover {
    transform: translateY(-2px);
    box-shadow:
      0 4px 6px -1px rgba(0, 0, 0, 0.05),
      0 10px 15px -3px rgba(0, 0, 0, 0.08);
  }

  .file-info {
    display: flex;
    align-items: center;
    gap: 1.25rem;
    flex: 1;
    min-width: 0;

    .file-type {
      width: 2.5rem;
      height: 2.5rem;
      background: linear-gradient(135deg, var(--accent-color) 0%, #60a5fa 100%);
      border-radius: 12px;
      display: flex;
      align-items: center;
      justify-content: center;
      color: white;
      font-size: 0.875rem;
      font-weight: 600;
      position: relative;
      transition: transform 0.3s ease;

      &::after {
        content: '';
        position: absolute;
        inset: 0;
        background: linear-gradient(45deg, transparent 40%, rgba(255, 255, 255, 0.3) 100%);
        border-radius: inherit;
      }

      &:hover {
        transform: scale(1.05) rotate(-5deg);
      }
    }

    .file-meta {
      flex: 1;
      min-width: 0;
      display: flex;
      flex-direction: column;
      gap: 0.375rem;

      .file-name {
        font:
          500 1rem/1.4 'Inter',
          sans-serif;
        color: #1e293b;
        letter-spacing: -0.015em;
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
        position: relative;

        &::after {
          content: '';
          position: absolute;
          bottom: -2px;
          left: 0;
          width: 0;
          height: 1px;
          background: var(--accent-color);
          transition: width 0.3s ease;
        }
      }

      .file-size {
        font-size: 0.8125rem;
        color: #64748b;
        background: rgba(224, 232, 255, 0.3);
        padding: 0.375rem 0.875rem;
        border-radius: 20px;
        width: fit-content;
        transition: all 0.2s ease;
      }
    }
  }

  .remove-btn {
    width: 2.25rem;
    height: 2.25rem;
    border: none;
    background: rgba(239, 68, 68, 0.08);
    color: var(--error-color);
    cursor: pointer;
    border-radius: 10px;
    display: flex;
    align-items: center;
    justify-content: center;
    transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
    position: relative;
    overflow: hidden;

    &::before {
      content: '×';
      font-size: 1.75rem;
      font-weight: 300;
      transition: all 0.3s ease;
      position: relative;
      z-index: 1;
    }

    &::after {
      content: '';
      position: absolute;
      top: 0;
      left: 0;
      width: 100%;
      height: 100%;
      background: var(--error-color);
      opacity: 0;
      transition: opacity 0.3s ease;
    }

    &:hover {
      background: transparent;
      transform: rotate(90deg) scale(1.05);

      &::before {
        color: white;
        transform: scale(1.15);
      }

      &::after {
        opacity: 1;
      }
    }

    &:active {
      transform: rotate(90deg) scale(0.95);
    }
  }

  @media (max-width: 480px) {
    padding: 0.875rem;
    border-radius: 12px;

    .file-info {
      gap: 0.875rem;

      .file-type {
        width: 2rem;
        height: 2rem;
        font-size: 0.75rem;
      }

      .file-meta .file-name {
        font-size: 0.9375rem;
      }
    }

    .remove-btn {
      width: 2rem;
      height: 2rem;

      &::before {
        font-size: 1.5rem;
      }
    }
  }
}
</style>
