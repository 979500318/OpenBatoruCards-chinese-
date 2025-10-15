package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.stock.StockAbilityAssassin;

public final class SIGNI_K1_GhostPhantomApparition extends Card {

    public SIGNI_K1_GhostPhantomApparition()
    {
        setImageSets("WX25-P1-100");

        setOriginalName("幻怪　ゴースト");
        setAltNames("ゲンカイゴースト Genkai Goosuto");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、手札から＜怪異＞のシグニを１枚捨ててもよい。そうした場合、ターン終了時まで、このシグニは[[アサシン（パワー3000以下のシグニ）]]を得る。"
        );

        setName("en", "Ghost, Phantom Apparition");
        setDescription("en",
                "@U: At the beginning of your attack phase, you may discard 1 <<Apparition>> SIGNI from your hand. If you do, until end of turn, this SIGNI gains [[Assassin (SIGNI with power 3000 or less)]]."
        );

		setName("zh_simplified", "幻怪 幽灵");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，可以从手牌把<<怪異>>精灵1张舍弃。这样做的场合，直到回合结束时为止，这只精灵得到[[暗杀（力量3000以下的精灵）]]。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.APPARITION);
        setLevel(1);
        setPower(2000);

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
            if(getCardIndex().isSIGNIOnField() && discard(0,1, new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.APPARITION)).get() != null)
            {
                attachAbility(getCardIndex(), new StockAbilityAssassin(this::onAttachedStockEffAddCond), ChronoDuration.turnEnd());
            }
        }
        private ConditionState onAttachedStockEffAddCond(CardIndex cardIndexOpposite)
        {
            return cardIndexOpposite.getIndexedInstance().getPower().getValue() <= 3000 ? ConditionState.OK : ConditionState.BAD;
        }
    }
}
