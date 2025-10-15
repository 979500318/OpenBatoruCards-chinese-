package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.cost.TrashCost;

public final class SIGNI_K1_OpposingMirrorsSmallTrap extends Card {

    public SIGNI_K1_OpposingMirrorsSmallTrap()
    {
        setImageSets("WX24-P3-089");

        setOriginalName("小罠　アワセカガミ");
        setAltNames("ショウビンアワセカガミ Shoubin Awasekagami");
        setDescription("jp",
                "@E：あなたのデッキの一番上を見る。そのカードをトラッシュに置いてもよい。\n" +
                "@A @[このシグニを場からトラッシュに置く]@：あなたのデッキの一番上を見る。そのカードを【マジックボックス】としてあなたのシグニゾーンに設置してもよい。"
        );

        setName("en", "Opposing Mirrors, Small Trap");
        setDescription("en",
                "@E: Look at the top card of your deck. You may put it into the trash.\n" +
                "@A @[Put this SIGNI from the field into the trash]@: Look at the top card of your deck. You may put that card onto 1 of your SIGNI zones as a [[Magic Box]]."
        );

		setName("zh_simplified", "小罠 无限反射镜");
        setDescription("zh_simplified", 
                "@E :看你的牌组最上面。可以把那张牌放置到废弃区。\n" +
                "@A 这只精灵从场上放置到废弃区:看你的牌组最上面。可以把那张牌作为[[魔术箱]]在你的精灵区设置。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.TRICK);
        setLevel(1);
        setPower(3000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(this::onEnterEff);
            
            registerActionAbility(new TrashCost(), this::onActionEff);
        }

        private void onEnterEff()
        {
            CardIndex cardIndex = look();
            
            if(cardIndex != null)
            {
                if(playerChoiceActivate())
                {
                    trash(cardIndex);
                } else {
                    returnToDeck(cardIndex, DeckPosition.TOP);
                }
            }
        }
        
        private void onActionEff()
        {
            CardIndex cardIndex = look();
            
            if(cardIndex != null)
            {
                if(playerChoiceActivate())
                {
                    putAsMagicBox(cardIndex);
                } else {
                    returnToDeck(cardIndex, DeckPosition.TOP);
                }
            }
        }
    }
}
