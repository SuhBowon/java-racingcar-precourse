package racingcar.model.laps;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

public class LapsManagerTest {
    @Test
    void 현재_랩_확인() {
        int expected = 5;

        Laps laps = mock(Laps.class);
        LapsDone lapsDone = mock(LapsDone.class);
        when(lapsDone.get()).thenReturn(expected);

        assertThat(new LapsManager(laps, lapsDone).getCurrentLaps()).isEqualTo(expected);
    }

    @Test
    void 다음_랩_이동() {
        LapsDone lapsDone = new LapsDone();
        Laps laps = mock(Laps.class);
        when(laps.get()).thenReturn(1000);
        LapsManager lapsManager = new LapsManager(laps, lapsDone);

        int expected = lapsDone.get() + 1;

        lapsManager.next();

        assertThat(lapsManager.getCurrentLaps()).isEqualTo(expected);
    }

    @Test
    void 다음_랩_이동_예외() {
        Laps laps = mock(Laps.class);
        LapsDone lapsDone = mock(LapsDone.class);
        when(laps.get()).thenReturn(10);
        when(lapsDone.get()).thenReturn(10);

        LapsManager lapsManager = new LapsManager(laps, lapsDone);

        assertThatThrownBy(() -> lapsManager.next()).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void 종료_여부_종료됨() {
        Laps laps = mock(Laps.class);
        LapsDone lapsDone = mock(LapsDone.class);
        when(laps.get()).thenReturn(10);
        when(lapsDone.get()).thenReturn(10);

        LapsManager lapsManager = new LapsManager(laps, lapsDone);

        assertThat(lapsManager.isFinished()).isTrue();
    }

    @Test
    void 종료_여부_종료안됨() {
        Laps laps = mock(Laps.class);
        LapsDone lapsDone = mock(LapsDone.class);
        when(laps.get()).thenReturn(10);
        when(lapsDone.get()).thenReturn(9);

        LapsManager lapsManager = new LapsManager(laps, lapsDone);

        assertThat(lapsManager.isFinished()).isFalse();
    }
}
