package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardFlag;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.game.FieldZone;

public final class SPELL_W_OpenGate extends Card {

    public SPELL_W_OpenGate()
    {
        setImageSets("WXDi-P15-079");

        setOriginalName("オープン・ゲート");
        setAltNames("オープンゲート Oopun Geeto");
        setDescription("jp",
                "あなたのデッキの上からカードを５枚見る。その中からシグニ１枚を【ゲート】があるあなたのシグニゾーンに出し、残りを好きな順番でデッキの一番下に置く。" +
                "~#：対戦相手のアップ状態のシグニ１体を対象とし、それを手札に戻す。"
        );

        setName("en", "Open Gate");
        setDescription("en",
                "Look at the top five cards of your deck. Put a SIGNI from among them into one of your SIGNI Zones with a [[Gate]]. Put the rest on the bottom of your deck in any order." +
                "~#Return target upped SIGNI on your opponent's field to its owner's hand."
        );
        
        setName("en_fan", "Open Gate");
        setDescription("en_fan",
                "Look at the top 5 cards of your deck. Put 1 SIGNI from among them onto 1 of your SIGNI zones with a [[Gate]], and put the rest on the bottom of your deck in any order." +
                "~#Target 1 of your opponent's upped SIGNI, and return it to their hand."
        );

		setName("zh_simplified", "打开·大门");
        setDescription("zh_simplified", 
                "从你的牌组上面看5张牌。从中把精灵1张在有[[大门]]的你的精灵区出场，剩下的任意顺序放置到牌组最下面。" +
                "~#对战对手的竖直状态的精灵1只作为对象，将其返回手牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SPELL);
        setColor(CardColor.WHITE);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerSpellAbility(this::onSpellEff);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onSpellEff()
        {
            look(5);
            
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.FIELD).own().SIGNI().fromLooked().playable()).get();
            if(cardIndex != null)
            {
                FieldZone fieldZone = playerTargetZone(new TargetFilter(TargetHint.FIELD).own().SIGNI().zone().withZoneObject(CardUnderType.ZONE_GATE).playable(cardIndex)).get();
                if(fieldZone != null) putOnField(cardIndex, fieldZone.getZoneLocation());
            }
            
            while(getLookedCount() > 0)
            {
                cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().upped()).get();
            addToHand(target);
        }
    }
}
