package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameLog;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.Enter;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_K4_YamiganeSuperTrap extends Card {

    public SIGNI_K4_YamiganeSuperTrap()
    {
        setImageSets("WXK01-045");

        setOriginalName("超罠　ヤミガネ");
        setAltNames("チョウビンヤミガネ Choubin Yamigane");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、このターンに場に出た対戦相手のシグニ１体を対象とし、%Kを支払ってもよい。そうした場合、それをバニッシュする。\n" +
                "@E %K：対戦相手のシグニ１体を対象とし、それをトラッシュに置く。対戦相手は自分のデッキを上から、それ以下のレベルを持つシグニがめくれるまで公開し、そのシグニを場に出す。そのシグニの@E能力は発動しない。この方法で公開されたカードをシャッフルしてデッキの一番下に置く。" +
                "~#：カードを１枚引く。あなたのライフクロスが４枚以下の場合、追加で【エナチャージ１】をする。"
        );

        setName("en", "Yamigane, Super Trap");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, target 1 of your opponent's SIGNI that entered the field during this turn, and you may pay %K. If you do, banish it.\n" +
                "@E %K: Target 1 of your opponent's SIGNI, and put it into the trash. Your opponent reveals cards from the top of their deck until they reveal a SIGNI with level equal to or lower than that SIGNI's, and puts it onto the field. Its @E abilities do not activate. Shuffle the rest and put them on the bottom of their deck." +
                "~#Draw 1 card. If you have 4 or less life cloth, additionally, [[Ener Charge 1]]."
        );

		setName("zh_simplified", "超罠 黑市贷款");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，这个回合出场的对战对手的精灵1只作为对象，可以支付%K。这样做的场合，将其破坏。\n" +
                "@E %K对战对手的精灵1只作为对象，将其放置到废弃区。对战对手从自己的牌组上面，直到把持有其的以下的等级的精灵公开为止，那张精灵出场。那只精灵的@E能力不能发动。这个方法公开的牌洗切放置到牌组最下面。" +
                "~#抽1张牌。你的生命护甲在4张以下的场合，追加[[能量填充1]]。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setLRIGType(CardLRIGType.GUZUKO);
        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClass.TRICK);
        setLevel(4);
        setPower(12000);

        setPlayFormat(PlayFormat.KEY);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
            
            registerEnterAbility(new EnerCost(Cost.color(CardColor.BLACK, 1)), this::onEnterEff);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onAutoEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().custom(cardIndex ->
                GameLog.getTurnRecordsCount(event -> event.getId() == GameEventId.ENTER && event.getCaller().getInstanceId() == cardIndex.getIndexedInstance().getInstanceId()) != 0
            )).get();
            banish(target);
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.TRASH).OP().SIGNI()).get();
            
            if(target != null)
            {
                int level = target.getIndexedInstance().getLevel().getValue();
                if(trash(target) && target.getLocation() == CardLocation.TRASH)
                {
                    CardIndex cardIndexSIGNI = revealUntil(getOpponent(), cardIndex -> cardIndex.getIndexedInstance().getLevel().getValue() <= level);
                    putOnField(cardIndexSIGNI, Enter.DONT_ACTIVATE);
                    
                    int countReturned = returnToDeck(getCardsInRevealed(getOpponent()), DeckPosition.BOTTOM);
                    shuffleDeck(getOpponent(), countReturned, DeckPosition.BOTTOM);
                }
            }
        }

        private void onLifeBurstEff()
        {
            draw(1);

            if(getLifeClothCount(getOwner()) <= 4)
            {
                enerCharge(1);
            }
        }
    }
}
