package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.data.ability.events.EventTarget;

public final class SIGNI_R3_DiabrideNaturalMarriageStone extends Card {
    
    public SIGNI_R3_DiabrideNaturalMarriageStone()
    {
        setImageSets("WXDi-P05-036", "SPDi10-13");
        
        setOriginalName("羅婚石　ダイヤブライド");
        setAltNames("ラコンセキダイヤブライド Rakonseki Daiyaburaido");
        setDescription("jp",
                "@U $1：あなたの赤のシグニ１体が対戦相手の、能力か効果の対象になったとき、対戦相手は自分のエナゾーンからカード１枚を選びトラッシュに置く。\n" +
                "@E @[手札を１枚捨てる]@：あなたのデッキの上からカードを２枚見る。その中からカード１枚を手札に加え、残りをトラッシュに置く。" +
                "~#：どちらか１つを選ぶ。\n" +
                "$$1対戦相手のアップ状態のシグニ１体を対象とし、それをバニッシュする。\n" +
                "$$2カードを１枚引く。"
        );
        
        setName("en", "Diabride, Natural Engagement Crystal");
        setDescription("en",
                "@U $T1: When a red SIGNI on your field becomes the target of your opponent's ability or effect, your opponent chooses a card from their Ener Zone and puts it into their trash.\n" +
                "@E @[Discard a card]@: Look at the top two cards of your deck. Add a card from among them to your hand and put the rest into your trash." +
                "~#Choose one -- \n$$1 Vanish target upped SIGNI on your opponent's field. \n$$2 Draw a card."
        );
        
        setName("en_fan", "Diabride, Natural Marriage Stone");
        setDescription("en_fan",
                "@U $T1: When 1 of your red SIGNI is targeted by your opponent's ability or effect, your opponent chooses 1 card from their ener zone and puts it into the trash.\n" +
                "@E @[Discard 1 card from your hand]@: Look at the top 2 cards of your deck. Add 1 card from among them to your hand, and put the rest into the trash." +
                "~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's upped SIGNI, and banish it.\n" +
                "$$2 Draw 1 card."
        );
        
		setName("zh_simplified", "罗婚石 钻石新娘");
        setDescription("zh_simplified", 
                "@U $T1 :当你的红色的精灵1只被作为对战对手的，能力或效果的对象时，对战对手从自己的能量区选1张牌放置到废弃区。\n" +
                "@E 手牌1张舍弃:从你的牌组上面看2张牌。从中把1张牌加入手牌，剩下的放置到废弃区。" +
                "~#以下选1种。\n" +
                "$$1 对战对手的竖直状态的精灵1只作为对象，将其破坏。\n" +
                "$$2 抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.GEM);
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
            
            AutoAbility auto = registerAutoAbility(GameEventId.TARGET, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 1);
            
            registerEnterAbility(new DiscardCost(1), this::onEnterEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return getEvent().getSourceAbility() != null && !isOwnCard(getEvent().getSourceCardIndex()) &&
                   isOwnCard(caller) && CardLocation.isSIGNI(caller.getLocation()) && caller.getIndexedInstance().getColor().matches(CardColor.RED) &&
                   EventTarget.getDataSourceTargetRole() != caller.getIndexedInstance().getCurrentOwner() ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex cardIndex = playerTargetCard(getOpponent(), new TargetFilter(TargetHint.BURN).own().fromEner()).get();
            trash(cardIndex);
        }
        
        private void onEnterEff()
        {
            look(2);
            
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().fromLooked()).get();
            addToHand(cardIndex);
            
            trash(getCardsInLooked(getOwner()));
        }
        
        private void onLifeBurstEff()
        {
            if(playerChoiceMode() == 1)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().upped()).get();
                banish(target);
            } else {
                draw(1);
            }
        }
    }
}
