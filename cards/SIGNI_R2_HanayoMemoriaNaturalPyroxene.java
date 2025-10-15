package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition;
import open.batoru.data.ability.AbilityConst.ChoiceLogic;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_R2_HanayoMemoriaNaturalPyroxene extends Card {

    public SIGNI_R2_HanayoMemoriaNaturalPyroxene()
    {
        setImageSets("WXDi-P08-039", "WXDi-P08-039P");

        setOriginalName("羅輝石　花代//メモリア");
        setAltNames("ラキセキハナヨメモリア Rakiseki Hanayo Memoria");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたのエナゾーンにカードがない場合、次の対戦相手のターン終了時まで、このシグニの基本レベルは３になり、基本パワーは12000になる。\n" +
                "@U：あなたのアタックフェイズ開始時、このシグニのレベル以下の対戦相手のシグニ１体を対象とし、手札から赤のカードか＜宝石＞のシグニを合計２枚捨ててもよい。そうした場合、それをバニッシュする。"
        );

        setName("en", "Hanayo//Memoria, Natural Pyroxene");
        setDescription("en",
                "@U: At the beginning of your attack phase, if there are no cards in your Ener Zone, the base level of this SIGNI becomes three and its base power becomes 12000 until the end of your opponent's next end phase.\n" +
                "@U: At the beginning of your attack phase, you may discard a total of two red cards or <<Jewel>> SIGNI. If you do, vanish target SIGNI on your opponent's field with a level less than or equal to this SIGNI."
        );
        
        setName("en_fan", "Hanayo//Memoria, Natural Pyroxene");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, if there are 0 cards in your ener zone, until the end of your opponent's next turn, this SIGNI's base level becomes 3 and its base power becomes 12000.\n" +
                "@U: At the beginning of your attack phase, target 1 of your opponent's SIGNI with level equal to or lower than this SIGNI's, and you may discard 2 red cards and/or 2 <<Gem>> SIGNI from your hand. If you do, banish it."
        );

		setName("zh_simplified", "罗辉石 花代//回忆");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的能量区没有牌的场合，直到下一个对战对手的回合结束时为止，这只精灵的基本等级变为3，基本力量变为12000。\n" +
                "@U :你的攻击阶段开始时，这只精灵的等级以下的对战对手的精灵1只作为对象，可以从手牌把红色的牌或<<宝石>>精灵合计2张舍弃。这样做的场合，将其破坏。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.GEM);
        setLevel(2);
        setPower(5000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto1 = registerAutoAbility(GameConst.GameEventId.PHASE_START, this::onAutoEff1);
            auto1.setCondition(this::onAutoEffCond);

            AutoAbility auto2 = registerAutoAbility(GameConst.GameEventId.PHASE_START, this::onAutoEff2);
            auto2.setCondition(this::onAutoEffCond);
        }

        private AbilityCondition.ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GameConst.GamePhase.ATTACK_PRE ? AbilityCondition.ConditionState.OK : AbilityCondition.ConditionState.BAD;
        }

        private void onAutoEff1(CardIndex caller)
        {
            if(getEnerCount(getOwner()) == 0)
            {
                setBaseValue(getCardIndex(), getLevel(),3, ChronoDuration.nextTurnEnd(getOpponent()));
                setBasePower(getCardIndex(), 12000, ChronoDuration.nextTurnEnd(getOpponent()));
            }
        }
        private void onAutoEff2(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withLevel(0,getLevelByRef())).get();
            
            if(target != null &&
               discard(0,2, ChoiceLogic.BOOLEAN, new TargetFilter().fromHand().or(new TargetFilter().withColor(CardColor.RED), new TargetFilter().SIGNI().withClass(CardSIGNIClass.GEM).fromHand())).size() == 2)
            {
                banish(target);
            }
        }
    }
}
