# JavaFX Threading Checklist

## Implementation
- [ ] Long-running work wrapped in `Task` or `Service`
- [ ] `call()` method contains no UI code
- [ ] `onSucceeded`/`setOnFailed` handlers update UI
- [ ] User gets visual feedback (progress indicator, status label)
- [ ] Cancellation supported (check `isCancelled()` in loops)

## Code Review
- [ ] No direct DB/network/file I/O on FX thread
- [ ] No `ObservableList` modification from background thread
- [ ] No `Thread.sleep()` anywhere in UI code path
- [ ] Exception handling present on all async operations
- [ ] `Service` used for restartable work, `Task` for one-shot
