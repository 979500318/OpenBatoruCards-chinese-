package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_K2_CodeAntiPetroglyph extends Card {

    public SIGNI_K2_CodeAntiPetroglyph()
    {
        setImageSets("WX25-P1-103");

        setOriginalName("コードアンチ　ペトログリフ");
        setAltNames("コードアンチペトログリフ Koodo Anchi Petorogurifu");
        setDescription("jp",
                "@E：あなたのデッキの上からカードを３枚見る。その中からカードを１枚までトラッシュに置き、残りを好きな順番でデッキの一番下に置く。その後、この効果で＜古代兵器＞のシグニをトラッシュに置いた場合、対戦相手のシグニ１体を対象とし、あなたのエナゾーンから＜古代兵器＞のシグニ１枚をトラッシュに置いてもよい。そうした場合、ターン終了時まで、それのパワーを－5000する。"
        );

        setName("en", "Code Anti Petroglyph");
        setDescription("en",
                "@E: Look at the top 3 cards of your deck. Put up to 1 card from among them into the trash, and put the rest on the bottom of your deck in any order. Then, if you put a <<Ancient Weapon>> SIGNI into the trash this way, target 1 of your opponent's SIGNI, and you may put 1 <<Ancient Weapon>> SIGNI from your ener zone into the trash. If you do, until end of turn, it gets --5000 power."
        );

		setName("zh_simplified", "古兵代号 岩刻");
        setDescription("zh_simplified", 
                "@E :从你的牌组上面看3张牌。从中把牌1张最多放置到废弃区，剩下的任意顺序放置到牌组最下面。然后，这个效果把<<古代兵器>>精灵放置到废弃区的场合，对战对手的精灵1只作为对象，可以从你的能量区把<<古代兵器>>精灵1张放置到废弃区。这样做的场合，直到回合结束时为止，其的力量-5000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANCIENT_WEAPON);
        setLevel(2);
        setPower(5000);

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
        }

        private void onEnterEff()
        {
            look(3);
            
            CardIndex cardIndexTrash = playerTargetCard(0,1, new TargetFilter(TargetHint.TRASH).own().fromLooked()).get();
            boolean trashed = trash(cardIndexTrash);
            
            while(getLookedCount() > 0)
            {
                CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
            
            if(trashed && cardIndexTrash.getIndexedInstance().getSIGNIClass().matches(CardSIGNIClass.ANCIENT_WEAPON))
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
                
                if(target != null)
                {
                    CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.TRASH).own().SIGNI().withClass(CardSIGNIClass.ANCIENT_WEAPON).fromEner()).get();
                    
                    if(trash(cardIndex))
                    {
                        gainPower(target, -5000, ChronoDuration.turnEnd());
                    }
                }
            }
        }
    }
}
