package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.CardDataImageSet.Mask;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.stock.StockPlayerAbilityLRIGBarrier;
import open.batoru.data.ability.stock.StockPlayerAbilitySIGNIBarrier;

public final class ARTS_X_OpenDreamLand extends Card {

    public ARTS_X_OpenDreamLand()
    {
        setImageSets(Mask.VERTICAL+"PR-Di035");
        setLinkedImageSets(Token_SIGNIBarrier.IMAGE_SET, Token_LRIGBarrier.IMAGE_SET);

        setOriginalName("OPEN DREAM LAND!");
        setAltNames("オープンドリームランド Oopan Doriimu Rando");
        setDescription("jp",
                "あなたのトラッシュから＜プリパラ＞のシグニを３枚まで対象とし、それらを手札に加える。次のあなたのアタックフェイズ開始時、あなたの場にそれぞれ共通する色を持ちレベルの異なる＜プリパラ＞のシグニが３体あり、\n" +
                "その色が白の場合、【シグニバリア】１つと【ルリグバリア】１つを得る。\n" +
                "赤の場合、対戦相手のライフクロス１枚をトラッシュに置く。\n" +
                "青の場合、カードを３枚引き、対戦相手は手札を３枚捨てる。\n" +
                "緑の場合、対戦相手のすべてのシグニをエナゾーンに置く。\n" +
                "黒の場合、対戦相手のデッキの上からカードを２０枚トラッシュに置く。"
        );

        setName("en", "OPEN DREAM LAND!");
        setDescription("en",
                "Target up to 3 <<PriPara>> SIGNI from your trash, and add them to your hand. At the beginning of your next attack phase, if there are 3 <<PriPara>> SIGNI with different levels that each share a common color on your field,\n" +
                "If they are white, gain 1 [[LRIG Barrier]] and 1 [[SIGNI Barrier]].\n" +
                "If they are red, put 1 of your opponent's life cloth into the trash.\n" +
                "If they are blue, draw 3 cards, and your opponent discards 3 cards from their hand.\n" +
                "If they are green, put all of your opponent's SIGNI into the ener zone.\n" +
                "If they are black, put the top 20 cards of your opponent's deck into the trash."
        );

		setName("zh_simplified", "OPEN DREAM LAND!");
        setDescription("zh_simplified", 
                "从你的废弃区把<<プリパラ>>精灵3张最多作为对象，将这些加入手牌。下一个你的攻击阶段开始时，你的场上的持有共通颜色的等级不同的<<プリパラ>>精灵在3只，\n" +
                "其的颜色有白色的场合，得到[[精灵屏障]]1个和[[分身屏障]]1个。\n" +
                "红色的场合，对战对手的生命护甲1张放置到废弃区。\n" +
                "蓝色的场合，抽3张牌，对战对手把手牌3张舍弃。\n" +
                "绿色的场合，对战对手的全部的精灵放置到能量区。\n" +
                "黑色的场合，从对战对手的牌组上面把20张牌放置到废弃区。\n"
        );

        setType(CardType.ARTS);
        setCost(Cost.colorless(2));
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerARTSAbility(this::onARTSEff);
        }

        private void onARTSEff()
        {
            DataTable<CardIndex> data = playerTargetCard(0,3, new TargetFilter(TargetHint.HAND).own().SIGNI().withClass(CardSIGNIClass.PRIPARA).fromTrash());
            addToHand(data);
            
            callDelayedEffect(ChronoDuration.nextPhase(getOwner(), GamePhase.ATTACK_PRE), () -> {
                DataTable<CardIndex> dataOnField = new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.PRIPARA).getExportedData();
                if(dataOnField.size() != 3 ||
                   !dataOnField.stream().allMatch(cardIndex -> cardIndex.getIndexedInstance().getColor().matches(dataOnField.get().getIndexedInstance().getColor())) ||
                    dataOnField.stream().map(cardIndex -> cardIndex.getIndexedInstance().getLevel().getValue()).distinct().count() != 3) return;
                
                if(dataOnField.stream().allMatch(cardIndex -> cardIndex.getIndexedInstance().getColor().matches(CardColor.WHITE)))
                {
                    attachPlayerAbility(getOwner(), new StockPlayerAbilityLRIGBarrier(), ChronoDuration.permanent());
                    attachPlayerAbility(getOwner(), new StockPlayerAbilitySIGNIBarrier(), ChronoDuration.permanent());
                }
                if(dataOnField.stream().allMatch(cardIndex -> cardIndex.getIndexedInstance().getColor().matches(CardColor.RED)))
                {
                    trash(getOpponent(), CardLocation.LIFE_CLOTH);
                }
                if(dataOnField.stream().allMatch(cardIndex -> cardIndex.getIndexedInstance().getColor().matches(CardColor.BLUE)))
                {
                    draw(3);
                    discard(getOpponent(), 3);
                }
                if(dataOnField.stream().allMatch(cardIndex -> cardIndex.getIndexedInstance().getColor().matches(CardColor.GREEN)))
                {
                    putInEner(getSIGNIOnField(getOpponent()));
                }
                if(dataOnField.stream().allMatch(cardIndex -> cardIndex.getIndexedInstance().getColor().matches(CardColor.BLACK)))
                {
                    millDeck(getOpponent(), 20);
                }
            });
        }
    }
}
