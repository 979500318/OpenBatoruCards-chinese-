package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.ChoiceLogic;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_X3_REVEncoring extends Card {

    public SIGNI_X3_REVEncoring()
    {
        setImageSets("PR-Di017B");

        setOriginalName("REV:アンコーリング");
        setAltNames("レベリオンアンコーリング Reberion Ankooringu");
        setDescription("jp",
                "（場以外の領域にあるかぎり、このカードは≪白熱する黒白≫である。このカードが場を離れると≪白熱する黒白≫に戻る）\n\n" +
                "@U：あなたのアタックフェイズ開始時、対戦相手のシグニ1体を対象とし、手札を3枚捨ててもよい。そうした場合、それをトラッシュに置く。"
        );

        setName("en", "REV: Encoring");
        setDescription("en",
                "((As long as this card is in an area not on the field, it is \"Incandescent Black and White\". When this card leaves the field, it returns to being \"Incandescent Black and White\"))\n\n" +
                "@U: At the beginning of your attack phase, target 1 of your opponent's SIGNI, and you may discard 3 cards from your hand. If you do, put it into the trash."
        );

		setName("zh_simplified", "REV：安可连接");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，对战对手的精灵1只作为对象，可以把手牌3张舍弃。这样做的场合，将其放置到废弃区。\n"
        );

        setType(CardType.SIGNI);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ORIGIN);
        setLevel(3);
        setPower(10000);

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
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.TRASH).OP().SIGNI()).get();
            
            if(target != null && discard(0,3, ChoiceLogic.BOOLEAN).get() != null)
            {
                trash(target);
            }
        }
    }
}
