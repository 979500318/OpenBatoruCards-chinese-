package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.ChoiceLogic;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.stock.StockAbilityAssassin;

public final class SIGNI_R3_TeppenDissonaCrimsonAngelPrincess extends Card {

    public SIGNI_R3_TeppenDissonaCrimsonAngelPrincess()
    {
        setImageSets("WXDi-P13-048");
        setLinkedImageSets("WXDi-P13-007");

        setOriginalName("紅天姫　テッペン//ディソナ");
        setAltNames("コウテンキテッペンディソナ Koutenki Teppen Disona");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたの手札が１枚以下の場合、【エナチャージ１】をする。\n" +
                "@U：このシグニがアタックしたとき、あなたの場に《王手の一歩　ヒラナ》がいる場合、あなたのエナゾーンから#Sのカード３枚をトラッシュに置いてもよい。そうした場合、ターン終了時まで、このシグニは【アサシン】を得る。" +
                "~#：対戦相手のパワー10000以下のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Apex//Dissona, Crimson Angel Queen");
        setDescription("en",
                "@U: At the beginning of your attack phase, if you have one or fewer cards in your hand, [[Ener Charge 1]].\n@U: Whenever this SIGNI attacks, if \"Hirana, a Step Towards the Check\" is on your field, you may put three #S cards from your Ener Zone into your trash. If you do, this SIGNI gains [[Assassin]] until end of turn." +
                "~#Vanish target SIGNI on your opponent's field with power 10000 or less."
        );
        
        setName("en_fan", "Teppen//Dissona, Crimson Angel Princess");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, if there are 1 or less cards in your hand, [[Ener Charge 1]].\n" +
                "@U: Whenever this SIGNI attacks, if your LRIG is \"Hirana, One Step Towards Check\", you may put 3 #S @[Dissona]@ cards from your ener zone into the trash. If you do, until end of turn, this SIGNI gains [[Assassin]]." +
                "~#Target 1 of your opponent's SIGNI with power 10000 or less, and banish it."
        );

		setName("zh_simplified", "红天姬 冲刺//失调");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的手牌在1张以下的场合，[[能量填充1]]。\n" +
                "@U 当这只精灵攻击时，你的场上有《王手の一歩　ヒラナ》的场合，可以从你的能量区把#S的牌3张放置到废弃区。这样做的场合，直到回合结束时为止，这只精灵得到[[暗杀]]。" +
                "~#对战对手的力量10000以下的精灵1只作为对象，将其破坏。\n"
        );

        setCardFlags(CardFlag.DISSONA | CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANGEL);
        setLevel(3);
        setPower(10000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            AutoAbility auto1 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff1);
            auto1.setCondition(this::onAutoEff1Cond);
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff2);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onAutoEff1Cond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            if(getHandCount(getOwner()) <= 1)
            {
                enerCharge(1);
            }
        }
        
        private void onAutoEff2()
        {
            if(getLRIG(getOwner()).getIndexedInstance().getName().getValue().contains("王手の一歩　ヒラナ"))
            {
                DataTable<CardIndex> data = playerTargetCard(0,3, ChoiceLogic.BOOLEAN, new TargetFilter(TargetHint.TRASH).own().dissona().fromEner());
                
                if(trash(data) == 3)
                {
                    attachAbility(getCardIndex(), new StockAbilityAssassin(), ChronoDuration.turnEnd());
                }
            }
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,10000)).get();
            banish(target);
        }
    }
}

