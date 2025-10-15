package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.Enter;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIGA_K2_GuzukoSadness extends Card {

    public LRIGA_K2_GuzukoSadness()
    {
        setImageSets("WXDi-P14-039");

        setOriginalName("グズ子～サドネス～");
        setAltNames("グズコサドネス Guzuko Sadonesu");
        setDescription("jp",
                "@E：シグニのカード名１つを宣言する。あなたのデッキの上から宣言したカードがめくれるまで公開する。そのシグニを場に出し、残りをトラッシュに置く。この方法で場に出たシグニの@E能力は発動しない。\n" +
                "@E %K %X %X：あな たのトラッシュからシグニ１枚を対象とし、それを場に出す。それの@E能力は発動しない。"
        );

        setName("en", "Guzuko ~Punishment~");
        setDescription("en",
                "@E: Declare the card name of a SIGNI. Reveal the top card of your deck until you turn over the declared card. Put that SIGNI onto your field and put the rest into your trash. The @E abilities of SIGNI put onto the field this way do not activate.\n@E %K %X %X: Put target SIGNI from your trash onto your field. The @E abilities of SIGNI put onto the field this way do not activate."
        );
        
        setName("en_fan", "Guzuko~Sadness~");
        setDescription("en_fan",
                "@E: Declare a SIGNI card name. Reveal cards from the top of your deck, until you reveal the declared card. Put it onto the field, and put the rest into the trash. The @E abilities of the SIGNI put onto the field this way don't activate.\n" +
                "@E %K %X %X: Target 1 SIGNI from your trash, and put it onto the field. Its @E abilities don't activate."
        );

		setName("zh_simplified", "迟钝子～伤痛～");
        setDescription("zh_simplified", 
                "@E 精灵的牌名1种宣言。从你的牌组上面直到把宣言的牌公开为止。那张精灵出场，剩下的放置到废弃区。这个方法出场的精灵的@E能力不能发动。\n" +
                "@E %K%X %X从你的废弃区把精灵1张作为对象，将其出场。其的@E能力不能发动。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.GUZUKO);
        setColor(CardColor.BLACK);
        setCost(Cost.colorless(1));
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(this::onEnterEff1);
            registerEnterAbility(new EnerCost(Cost.color(CardColor.BLACK, 1) + Cost.colorless(2)), this::onEnterEff2);
        }

        private void onEnterEff1()
        {
            Card chosenCard = playerChoiceCatalog(filter -> filter.filterType(CardType.SIGNI));
            
            CardIndex cardIndexFound = revealUntil(getOwner(), cardIndex -> cardIndex.getIndexedInstance().getName().getValue().contains(chosenCard.getOriginalName()));
            putOnField(cardIndexFound, Enter.DONT_ACTIVATE);

            trash(getCardsInRevealed(getOwner()));
        }

        private void onEnterEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FIELD).own().SIGNI().fromTrash().playable()).get();
            putOnField(target, Enter.DONT_ACTIVATE);
        }
    }
}

