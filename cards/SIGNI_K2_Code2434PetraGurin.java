package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_K2_Code2434PetraGurin extends Card {

    public SIGNI_K2_Code2434PetraGurin()
    {
        setImageSets("WXDi-CP01-049");

        setOriginalName("コード２４３４　ペトラ グリン");
        setAltNames("コードニジサンジペトラグリン Koodo Nijisanji Petora Gurin");
        setDescription("jp",
                "@E：あなたのデッキの上からカードを３枚トラッシュに置く。この方法で＜バーチャル＞のシグニが３枚トラッシュに置かれた場合、次の対戦相手のターン終了時まで、このシグニのパワーを＋4000する。" +
                "~#：対戦相手のシグニ１体を対象とし、手札を１枚捨ててもよい。そうした場合、ターン終了時まで、それのパワーを－12000する。"
        );

        setName("en", "Petra Gurin, Code 2434");
        setDescription("en",
                "@E: Put the top three cards of your deck into your trash. If exactly three <<Virtual>> SIGNI were put into your trash this way, this SIGNI gets +4000 power until the end of your opponent's next end phase." +
                "~#You may discard a card. If you do, target SIGNI on your opponent's field gets --12000 power until end of turn."
        );
        
        setName("en_fan", "Code 2434 Petra Gurin");
        setDescription("en_fan",
                "@E: Put the top 3 cards of your deck into the trash. If 3 <<Virtual>> SIGNI were put into the trash this way, until the end of your opponent's next turn, this SIGNI gets +4000 power." +
                "~#Target 1 of your opponent's SIGNI, and you may discard 1 card from your hand. If you do, until end of turn, it gets --12000 power."
        );

		setName("zh_simplified", "2434代号 Petra Gurin");
        setDescription("zh_simplified", 
                "@E :从你的牌组上面把3张牌放置到废弃区。这个方法把<<バーチャル>>精灵3张放置到废弃区的场合，直到下一个对战对手的回合结束时为止，这只精灵的力量+4000。" +
                "~#对战对手的精灵1只作为对象，可以把手牌1张舍弃。这样做的场合，直到回合结束时为止，其的力量-12000。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VIRTUAL);
        setLevel(2);
        setPower(8000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(this::onEnterEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onEnterEff()
        {
            DataTable<CardIndex> data = millDeck(3);
            
            if(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.VIRTUAL).fromTrash().match(data).getValidTargetsCount() == 3)
            {
                gainPower(getCardIndex(), 4000, ChronoDuration.nextTurnEnd(getOpponent()));
            }
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            
            if(target != null && discard(0,1).get() != null)
            {
                gainPower(target, -12000, ChronoDuration.turnEnd());
            }
        }
    }
}
