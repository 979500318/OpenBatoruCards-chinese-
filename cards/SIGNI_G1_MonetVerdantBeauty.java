package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_G1_MonetVerdantBeauty extends Card {

    public SIGNI_G1_MonetVerdantBeauty()
    {
        setImageSets("WX24-P3-080");

        setOriginalName("翠美　モネ");
        setAltNames("スイビモネ Suibi Mone");
        setDescription("jp",
                "@U：あなたのターン終了時、あなたのエナゾーンに＜美巧＞のシグニがある場合、次の対戦相手のターン終了時まで、このシグニのパワーを＋5000する。"
        );

        setName("en", "Monet, Verdant Beauty");
        setDescription("en",
                "@U: At the end of your turn, if there is a <<Beautiful Technique>> SIGNI in your ener zone, until the end of your opponent's next turn, this SIGNI gets +5000 power."
        );

		setName("zh_simplified", "翠美 莫奈");
        setDescription("zh_simplified", 
                "@U :你的回合结束时，你的能量区有<<美巧>>精灵的场合，直到下一个对战对手的回合结束时为止，这只精灵的力量+5000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BEAUTIFUL_TECHNIQUE);
        setLevel(1);
        setPower(3000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        // Contributed by NebelTal
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
            if(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.BEAUTIFUL_TECHNIQUE).fromEner().getValidTargetsCount() >= 1)
            {
                gainPower(getCardIndex(), 5000, ChronoDuration.nextTurnEnd(getOpponent()));
            }
        }
    }
}
