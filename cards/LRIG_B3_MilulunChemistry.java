package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.ChronoRecordScheduler.ChronoRecord;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.SpellAbility;
import open.batoru.data.ability.cost.BanishCost;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.game.gfx.GFXCardTextureLayer;

public final class LRIG_B3_MilulunChemistry extends Card {

    public LRIG_B3_MilulunChemistry()
    {
        setImageSets("WX25-P1-022", "WX25-P1-022U");

        setOriginalName("ミルルン・ケミストリー");
        setAltNames("ミルルンケミストリー Mirurun Kemisutorii");
        setDescription("jp",
                "@A $T1 @[レベル２以下の＜原子＞のシグニ１体をバニッシュする]@：あなたのデッキの上からカードを３枚見る。その中からスペルか＜原子＞のシグニ１枚を公開し手札に加え、残りを好きな順番でデッキの一番下に置く。\n" +
                "@A $G1 @[@|ハブニング|@]@ %B0：あなたと対戦相手のトラッシュからスペルをそれぞれ１枚まで対象とし、このターン、あなたはそれらを使用してもよい。"
        );

        setName("en", "Milulun Chemistry");
        setDescription("en",
                "@A $T1 @[Banish 1 level 2 or lower <<Atom>> SIGNI]@: Look at the top 3 cards of your deck. Reveal 1 spell or 1 <<Atom>> SIGNI from among them, and add it to your hand, and put the rest on the bottom of your deck in any order.\n" +
                "@A $G1 @[@|Happening|@]@ %B0: Target up to 1 spell each from your and your opponent's trash, and this turn, you may use them."
        );

		setName("zh_simplified", "米璐璐恩·化学");
        setDescription("zh_simplified", 
                "@A $T1 等级2以下的<<原子>>精灵1只破坏:从你的牌组上面看3张牌。从中把魔法或<<原子>>精灵1张公开加入手牌，剩下的任意顺序放置到牌组最下面。\n" +
                "@A $G1 乍现%B0:从你和对战对手的废弃区把魔法各1张最多作为对象，这个回合，你可以将这些使用。（把费用支付）\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.MILULUN);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 2));
        setLevel(3);
        setLimit(6);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            ActionAbility act1 = registerActionAbility(new BanishCost(new TargetFilter().withClass(CardSIGNIClass.ATOM).withLevel(0,2)), this::onActionEff1);
            act1.setUseLimit(UseLimit.TURN, 1);

            ActionAbility act2 = registerActionAbility(new EnerCost(Cost.color(CardColor.BLUE, 0)), this::onActionEff2);
            act2.setUseLimit(UseLimit.GAME, 1);
            act2.setName("Happening");
        }
        
        private void onActionEff1()
        {
            look(3);
            
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().or(new TargetFilter().spell(), new TargetFilter().SIGNI().withClass(CardSIGNIClass.ATOM).fromLooked()).fromLooked()).get();
            reveal(cardIndex);
            addToHand(cardIndex);
            
            while(getLookedCount() > 0)
            {
                cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }

        private void onActionEff2()
        {
            DataTable<CardIndex> data = playerTargetCard(new TargetFilter().own().spell().fromTrash());
            data.add(playerTargetCard(new TargetFilter().OP().spell().fromTrash()).get());
            
            if(data.get() != null)
            {
                reveal(data);
                trash(data);
                
                for(int i=0;i<data.size();i++)
                {
                    CardIndex target = data.get(i);
                    SpellAbility.findSpellAbility(target).ifPresent(ability -> {
                        ChronoRecord record = new ChronoRecord(target, ChronoDuration.turnEnd());
                        GFXCardTextureLayer.attachToChronoRecord(record, new GFXCardTextureLayer(target, "lun_stars"));
                        
                        gainValue(target, ability.getFlags(),AbilityFlag.CAN_USE_AS_MANUAL_ACTION, record);
                    });
                }
            }
        }
    }
}

