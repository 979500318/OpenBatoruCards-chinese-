package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_B3_MilulunFessoneNaturalSourcePrincess extends Card {

    public SIGNI_B3_MilulunFessoneNaturalSourcePrincess()
    {
        setImageSets("WXDi-P16-050", "WXDi-P16-050P");

        setOriginalName("羅原姫　ミルルン//フェゾーネ");
        setAltNames("ラゲンヒメミルルンフェゾーネ Ragenhime Mirurun Fezoone");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、対戦相手のシグニ１体を対象とし、手札からスペルを１枚捨て%Bを支払ってもよい。そうした場合、それをバニッシュする。\n" +
                "@U：このシグニがアタックしたとき、対戦相手の手札を見てスペル１枚を選び、捨てさせる。" +
                "~#：どちらか１つを選ぶ。\n" +
                "$$1対戦相手のシグニを２体まで対象とし、それらをダウンする。\n" +
                "$$2カードを１枚引く。"
        );

        setName("en", "Milulun//Fesonne, Element Queen");
        setDescription("en",
                "@U: At the beginning of your attack phase, you may discard a spell and pay %B. If you do, vanish target SIGNI on your opponent's field.\n@U: Whenever this SIGNI attacks, look at your opponent's hand and choose a spell. Your opponent discards it." +
                "~#Choose one -- \n$$1Down up to two target SIGNI on your opponent's field. \n$$2Draw a card."
        );
        
        setName("en_fan", "Milulun//Fessone, Natural Source Princess");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, target 1 of your opponent's SIGNI, and you may discard 1 spell from your hand and pay %B. If you do, banish it.\n" +
                "@U: Whenever this SIGNI attacks, look at your opponent's hand, choose 1 spell from it, and discard it." +
                "~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target up to 2 of your opponent's SIGNI, and down them.\n" +
                "$$2 Draw 1 card."
        );

		setName("zh_simplified", "罗原姬 米璐璐恩//音乐节");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，对战对手的精灵1只作为对象，可以从手牌把魔法1张舍弃并支付%B。这样做的场合，将其破坏。\n" +
                "@U :当这只精灵攻击时，看对战对手的手牌选魔法1张，舍弃。" +
                "~#以下选1种。\n" +
                "$$1 对战对手的精灵2只最多作为对象，将这些横置。\n" +
                "$$2 抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ATOM);
        setLevel(3);
        setPower(10000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
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
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
            
            if(target != null && payAll(new DiscardCost(new TargetFilter().spell()), new EnerCost(Cost.color(CardColor.BLUE, 1))))
            {
                banish(target);
            }
        }
        
        private void onAutoEff2()
        {
            reveal(getHandCount(getOpponent()), getOpponent(), CardLocation.HAND, true);

            CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.DISCARD).OP().spell().fromRevealed()).get();
            discard(cardIndex);

            addToHand(getCardsInRevealed(getOpponent()));
        }

        private void onLifeBurstEff()
        {
            if(playerChoiceMode() == 1)
            {
                DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.DOWN).OP().SIGNI());
                down(data);
            } else {
                draw(1);
            }
        }
    }
}
