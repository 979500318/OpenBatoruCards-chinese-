package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.AbilityConst.Enter;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_G3_GaucheAgneseNaturalPlantPrincess extends Card {
    
    public SIGNI_G3_GaucheAgneseNaturalPlantPrincess()
    {
        setImageSets("WXDi-P02-044");
        
        setOriginalName("羅植姫　ゴーシュ・アグネーゼ");
        setAltNames("ラショクヒメゴーシュアグネーゼ Rashokuhime Gooshu Aguneeze");
        setDescription("jp",
                "@U：このシグニがバニッシュされたとき、このシグニをエナゾーンからデッキの一番下に置いてもよい。そうした場合、あなたのデッキの一番上を公開する。そのカードがレベル３のシグニの場合、そのシグニをダウン状態で場に出してもよい。\n" +
                "@U：あなたのターン終了時、あなたのエナゾーンからシグニを１枚まで対象とし、それを手札に加える。" +
                "~#：対戦相手のパワー7000以上のシグニ１体を対象とし、それをバニッシュする。"
        );
        
        setName("en", "Gauche Agnese, Natural Plant Queen");
        setDescription("en",
                "@U: When this SIGNI is vanished, you may put this SIGNI from your Ener Zone onto the bottom of your deck. If you do, reveal the top card of your deck. If that card is a level three SIGNI, you may put that SIGNI onto your field downed.\n" +
                "@U: At the end of your turn, add up to one target SIGNI from your Ener Zone to your hand." +
                "~#Vanish target SIGNI on your opponent's field with power 7000 or more."
        );
        
        setName("en_fan", "Gauche Agnese, Natural Plant Princess");
        setDescription("en_fan",
                "@U: When this SIGNI is banished, you may put this SIGNI from your ener zone on the bottom of your deck. If you do, reveal the top card of your deck. If that card is a level 3 SIGNI, you may put it onto the field downed.\n" +
                "@U: At the end of your turn, target up to 1 SIGNI from your ener zone, and add it to your hand." +
                "~#Target 1 of your opponent's SIGNI with power 7000 or more, and banish it."
        );
        
		setName("zh_simplified", "罗植姬 戈休·雅格尼泽");
        setDescription("zh_simplified", 
                "@U 当这只精灵被破坏时，可以把这张精灵从能量区放置到牌组最下面。这样做的场合，你的牌组最上面公开。那张牌是等级3的精灵的场合，可以把那张精灵以#D状态出场。\n" +
                "@U :你的回合结束时，从你的能量区把精灵1张最多作为对象，将其加入手牌。" +
                "~#对战对手的力量7000以上的精灵1只作为对象，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.PLANT);
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
            
            registerAutoAbility(GameEventId.BANISH, this::onAutoEff1);
            
            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff2);
            auto.setCondition(this::onAutoEff2Cond);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onAutoEff1()
        {
            if(getCardIndex().getLocation() == CardLocation.ENER && playerChoiceActivate() && returnToDeck(getCardIndex(), DeckPosition.BOTTOM))
            {
                CardIndex cardIndex = reveal();
                
                if(cardIndex != null &&
                  (!CardType.isSIGNI(cardIndex.getCardReference().getType()) || cardIndex.getIndexedInstance().getLevelByRef() != 3 ||
                   playerChoiceAction(ActionHint.FIELD, ActionHint.TOP) == 2 ||
                   !putOnField(cardIndex, Enter.DOWNED)))
                {
                    returnToDeck(cardIndex, DeckPosition.TOP);
                }
            }
        }
        
        private ConditionState onAutoEff2Cond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2(CardIndex caller)
        {
            CardIndex target = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().SIGNI().fromEner()).get();
            addToHand(target);
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(7000,0)).get();
            banish(target);
        }
    }
}
