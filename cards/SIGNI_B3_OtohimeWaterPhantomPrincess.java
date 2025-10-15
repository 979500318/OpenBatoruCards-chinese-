package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.DiscardCost;

public final class SIGNI_B3_OtohimeWaterPhantomPrincess extends Card {

    public SIGNI_B3_OtohimeWaterPhantomPrincess()
    {
        setImageSets("WXDi-P11-044");

        setOriginalName("幻水姫　オトヒメ");
        setAltNames("ゲンスイヒメオトヒメ Gensuihime Otohime");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、カードを２枚引く。\n" +
                "@E：あなたの手札が５枚以下の場合、%Xを支払わないかぎりこのシグニを場からトラッシュに置く。\n" +
                "@A $T1 @[手札を４枚捨てる]@：対戦相手のパワー10000以下のシグニ１体を対象とし、それをデッキの一番下に置く。"
        );

        setName("en", "Otohime, Aquatic Phantom Queen");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, draw two cards.\n" +
                "@E: If you have five or fewer cards in your hand, put this SIGNI on your field into its owner's trash unless you pay %X.\n" +
                "@A $T1 @[Discard four cards]@: Put target SIGNI on your opponent's field with power 10000 or less on the bottom of its owner's deck."
        );
        
        setName("en_fan", "Otohime, Water Phantom Princess");
        setDescription("en_fan",
                "@U: Whenever this SIGNI attacks, draw 2 cards.\n" +
                "@E: If there are 5 or less cards in your hand, put this SIGNI from the field into the trash unless you pay %X.\n" +
                "@A $T1 @[Discard 4 cards from your hand]@: Target 1 of your opponent's SIGNI with power 10000 or less, and put it on the bottom of their deck."
        );

		setName("zh_simplified", "幻水姬 乙姬");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，抽2张牌。\n" +
                "@E 你的手牌在5张以下的场合，如果不把%X:支付，那么这只精灵从场上放置到废弃区。\n" +
                "@A $T1 手牌4张舍弃:对战对手的力量10000以下的精灵1只作为对象，将其放置到牌组最下面。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WATER_BEAST);
        setLevel(3);
        setPower(12000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
            
            registerEnterAbility(this::onEnterEff);

            ActionAbility act = registerActionAbility(new DiscardCost(4), this::onActionEff);
            act.setUseLimit(UseLimit.TURN, 1);
        }
        
        private void onAutoEff()
        {
            draw(2);
        }
        
        private void onEnterEff()
        {
            if(CardLocation.isSIGNI(getCardIndex().getLocation()) && getHandCount(getOwner()) <= 5 && !payEner(Cost.colorless(1)))
            {
                trash(getCardIndex());
            }
        }
        
        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).OP().SIGNI().withPower(0,10000)).get();
            returnToDeck(target, DeckPosition.BOTTOM);
        }
    }
}
