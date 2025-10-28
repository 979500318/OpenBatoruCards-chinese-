package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;
import open.batoru.data.ability.ARTSAbility;
import open.batoru.data.ability.cost.CoinCost;

public final class ARTS_K_BloodyOut extends Card {

    public ARTS_K_BloodyOut()
    {
        setImageSets("WX17-023", "WDK04-008", "PR-K010");

        setOriginalName("ブラッディ・アウト");
        setAltNames("ブラッディアウト Buraddi Auto");
        setDescription("jp",
                "@[ベット]@ -- #C #C\n\n" +
                "あなたのシグニ１体を場からトラッシュに置く。\n" +
                "そうした場合、対象の対戦相手のレベル４以下のシグニ１体をバニッシュする。あなたがベットしていた場合、追加で対象の対戦相手のシグニ１体をバニッシュする。"
        );

        setName("en", "Bloody Out");
        setDescription("en",
                "@[Bet]@ -- #C #C\n\n" +
                "Put 1 of your SIGNI from the field into the trash.\n" +
                "If you do, target 1 of your opponent's level 4 or lower SIGNI, and banish it. If you bet, additionally target 1 of your opponent's SIGNI, and banish it."
        );

        setName("zh_simplified", "鲜血·放逐");
        setDescription("zh_simplified", 
                "下注—#C #C\n" +
                "你的精灵1只从场上放置到废弃区。\n" +
                "这样做的场合，对象的对战对手的等级4以下的精灵1只破坏。你下注的场合，追加对象的对战对手的精灵1只破坏。"
        );

        setType(CardType.ARTS);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 2));
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK | UseTiming.SPELLCUTIN);

        setPlayFormat(PlayFormat.KEY);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        private final ARTSAbility arts;
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            arts = registerARTSAbility(this::onARTSEff);
            arts.setBetCost(new CoinCost(2));
        }
        
        private void onARTSEff()
        {
            CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.TRASH).own().SIGNI()).get();
            if(trash(cardIndex))
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withLevel(0,4)).get();
                banish(target);
                
                if(arts.hasUsedBet())
                {
                    target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
                    banish(target);
                }
            }
        }
    }
}
