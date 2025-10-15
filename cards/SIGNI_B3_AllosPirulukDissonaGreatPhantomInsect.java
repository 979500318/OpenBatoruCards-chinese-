package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_B3_AllosPirulukDissonaGreatPhantomInsect extends Card {

    public SIGNI_B3_AllosPirulukDissonaGreatPhantomInsect()
    {
        setImageSets("WXDi-P12-048", "WXDi-P12-048P");

        setOriginalName("大幻蟲　アロス・ピルルク//ディソナ");
        setAltNames("ダイゲンチュウアロスピルルクディソナ Daigenchuu Arosu Piruruku Disona");
        setDescription("jp",
                "@U $T1：あなたのターンの間、あなたが#Sのカードを１枚捨てたとき、対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－3000する。\n" +
                "@U：このシグニがアタックしたとき、あなたのエナゾーンから#Sのカード１枚をトラッシュに置いてもよい。そうした場合、対戦相手の手札を１枚見ないで選び、捨てさせる。"
        );

        setName("en", "Allos Piruluk//Dissona, Giant Insect");
        setDescription("en",
                "@U $T1: During your turn, when you discard a #S card, target SIGNI on your opponent's field gets --3000 power until end of turn.\n@U: Whenever this SIGNI attacks, you may put a #S card from your Ener Zone into your trash. If you do, your opponent discards a card at random."
        );
        
        setName("en_fan", "Allos Piruluk//Dissona, Great Phantom Insect");
        setDescription("en_fan",
                "@U $T1: During your turn, when you discard 1 #S @[Dissona]@ card from your hand, target 1 of your opponent's SIGNI, and until end of turn, it gets --3000 power.\n" +
                "@U: Whenever this SIGNI attacks, you may put 1 #S @[Dissona]@ card from your ener zone into the trash. If you do, choose 1 card from your opponent's hand without looking, and discard it."
        );

		setName("zh_simplified", "大幻虫 阿洛斯·皮璐璐可//失调");
        setDescription("zh_simplified", 
                "@U $T1 你的回合期间，当你把#S的牌1张舍弃时，对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-3000。\n" +
                "@U 当这只精灵攻击时，可以从你的能量区把#S的牌1张放置到废弃区。这样做的场合，不看对战对手的手牌选1张，舍弃。\n"
        );

        setCardFlags(CardFlag.DISSONA);

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.MISFORTUNE_INSECT);
        setLevel(3);
        setPower(12000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto1 = registerAutoAbility(GameEventId.DISCARD, this::onAutoEff1);
            auto1.setCondition(this::onAutoEff1Cond);
            auto1.setUseLimit(UseLimit.TURN, 1);
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff2);
        }
        
        private ConditionState onAutoEff1Cond(CardIndex caller)
        {
            return isOwnTurn() && isOwnCard(caller) && caller.getIndexedInstance().isState(CardStateFlag.IS_DISSONA) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -3000, ChronoDuration.turnEnd());
        }
        
        private void onAutoEff2()
        {
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.TRASH).own().dissona().fromEner()).get();
            
            if(trash(cardIndex))
            {
                cardIndex = playerChoiceHand().get();
                discard(cardIndex);
            }
        }
    }
}
