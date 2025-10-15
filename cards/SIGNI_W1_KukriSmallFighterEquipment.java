package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.GameLog;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_W1_KukriSmallFighterEquipment extends Card {

    public SIGNI_W1_KukriSmallFighterEquipment()
    {
        setImageSets("WX24-D1-11");

        setOriginalName("小闘装　ククリ");
        setAltNames("ショウトウソウククリ Shoutousou Kukuri");
        setDescription("jp",
                "@U：あなたのターン終了時、このターンにあなたが白のアーツを使用していた場合、あなたの白のシグニ１体を対象とし、次の対戦相手のターン終了時まで、それのパワーを＋4000する。"
        );

        setName("en", "Kukri, Small Fighter Equipment");
        setDescription("en",
                "@U: At the end of your turn, if you used a white ARTS this turn, target 1 of your white SIGNI, and until the end of your opponent's next turn, it gets +4000 power."
        );

		setName("zh_simplified", "小斗装 库克力");
        setDescription("zh_simplified", 
                "@U :你的回合结束时，这个回合你把白色的必杀使用过的场合，你的白色的精灵1只作为对象，直到下一个对战对手的回合结束时为止，其的力量+4000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ARM);
        setLevel(1);
        setPower(3000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(GameLog.getTurnRecordsCount(event -> event.getId() == GameEventId.USE_ARTS && isOwnCard(event.getCaller()) && event.getCaller().getColor().matches(CardColor.WHITE)) > 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.PLUS).own().SIGNI().withColor(CardColor.WHITE)).get();
                gainPower(target, 4000, ChronoDuration.nextTurnEnd(getOpponent()));
            }
        }
    }
}
