package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityConst;

public final class SIGNI_K1_ChironomidPhantomInsect extends Card {

    public SIGNI_K1_ChironomidPhantomInsect()
    {
        setImageSets("SPDi01-124");

        setOriginalName("幻蟲　ユスリカ");
        setAltNames("ゲンチュウユスリカ Genchuu Yusurika");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、あなたのアップ状態のルリグを好きな数ダウンする。対戦相手のデッキの上からこの方法でダウンしたルリグのレベルの合計に１を加えた枚数のカードをトラッシュに置く。" +
                "~#：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－8000する。"
        );

        setName("en", "Chironomid, Phantom Insect");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, down any number of your upped LRIG. Put a number of cards from the top of your opponent's deck into the trash equal to the total levels of all LRIG downed this way + 1." +
                "~#Target 1 of your opponent's SIGNI, and until end of turn, it gets --8000 power."
        );

		setName("zh_simplified", "幻虫 摇蚊");
        setDescription("zh_simplified", 
                "@U 当这只精灵攻击时，你的竖直状态的分身任意数量#D。从对战对手的牌组上面把这个方法#D的分身的等级的合计加1的张数的牌放置到废弃区。" +
                "~#对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-8000。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.MISFORTUNE_INSECT);
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

            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onAutoEff()
        {
            DataTable<CardIndex> data = playerTargetCard(0,AbilityConst.MAX_UNLIMITED, new TargetFilter(TargetHint.DOWN).own().anyLRIG().upped());
            
            if(down(data) > 0)
            {
                millDeck(getOpponent(), data.stream().mapToInt(cardIndex -> cardIndex.getIndexedInstance().getLevel().getValue()).sum()+1);
            }
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -8000, ChronoDuration.turnEnd());
        }
    }
}
