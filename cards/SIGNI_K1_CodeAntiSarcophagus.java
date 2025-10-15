package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCostList;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.cost.TrashCost;

public final class SIGNI_K1_CodeAntiSarcophagus extends Card {

    public SIGNI_K1_CodeAntiSarcophagus()
    {
        setImageSets("WX25-P1-098");

        setOriginalName("コードアンチ　サルコファガス");
        setAltNames("コードアンチサルコファガス Koodo Anchi Sarukofagasu");
        setDescription("jp",
                "@E：あなたのデッキの上からカードを３枚見る。その中からカードを１枚までトラッシュに置き、残りを好きな順番でデッキの一番下に置く。\n" +
                "@A %X @[このシグニを場からトラッシュに置く]@：あなたのトラッシュからレベル２以上の＜古代兵器＞のシグニ１枚を対象とし、それを場に出す。"
        );

        setName("en", "Code Anti Sarcophagus");
        setDescription("en",
                "@E: Look at the top 3 cards of your deck. Put up to 1 card from among them into the trash, and put the rest on the bottom of your deck in any order.\n" +
                "@A %X @[Put this SIGNI from the field into the trash]@: Target 1 level 2 or higher <<Ancient Weapon>> SIGNI from your trash, and put it onto the field."
        );

		setName("zh_simplified", "古兵代号 石棺");
        setDescription("zh_simplified", 
                "@E :从你的牌组上面看3张牌。从中把牌1张最多放置到废弃区，剩下的任意顺序放置到牌组最下面。\n" +
                "@A %X这只精灵从场上放置到废弃区:从你的废弃区把等级2以上的<<古代兵器>>精灵1张作为对象，将其出场。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANCIENT_WEAPON);
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

            registerEnterAbility(this::onEnterEff);
            
            registerActionAbility(new AbilityCostList(new EnerCost(Cost.colorless(1)), new TrashCost()), this::onActionEff);
        }
        
        private void onEnterEff()
        {
            look(3);
            
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.TRASH).own().fromLooked()).get();
            trash(cardIndex);
            
            while(getLookedCount() > 0)
            {
                cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }
        
        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FIELD).own().SIGNI().withClass(CardSIGNIClass.ANCIENT_WEAPON).withLevel(2,0).fromTrash().playable()).get();
            putOnField(target);
        }
    }
}
