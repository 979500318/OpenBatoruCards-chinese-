package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_K1_ReonaWestPriParaIdol extends Card {

    public SIGNI_K1_ReonaWestPriParaIdol()
    {
        setImageSets("WXDi-P10-071");

        setOriginalName("プリパラアイドル　レオナ・ウェスト");
        setAltNames("プリパラアイドルレオナウェスト Puripara Aidoru Reona Uesuto");
        setDescription("jp",
                "@E：あなたのデッキの一番上のカードをトラッシュに置く。その後、この方法で＜プリパラ＞のシグニがトラッシュに置かれた場合、対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－2000する。"
        );

        setName("en", "Reona West, Pripara Idol");
        setDescription("en",
                "@E: Put the top card of your deck into your trash. Then, if a <<Pripara>> SIGNI was put into your trash this way, target SIGNI on your opponent's field gets --2000 power until end of turn."
        );
        
        setName("en_fan", "Reona West, PriPara Idol");
        setDescription("en_fan",
                "@E: Put the top card of your deck into the trash. Then, if a <<PriPara>> SIGNI was put into the trash this way, target 1 of your opponent's SIGNI, and until end of turn, it gets --2000 power."
        );

		setName("zh_simplified", "美妙天堂偶像 莉安娜·威斯特");
        setDescription("zh_simplified", 
                "@E :你的牌组最上面的牌放置到废弃区。然后，这个方法把<<プリパラ>>精灵放置到废弃区的场合，对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-2000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.PRIPARA);
        setLevel(1);
        setPower(2000);

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
        }
        
        private void onEnterEff()
        {
            CardIndex cardIndex = millDeck(1).get();
            
            if(cardIndex != null && cardIndex.getIndexedInstance().getSIGNIClass().matches(CardSIGNIClass.PRIPARA))
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
                gainPower(target, -2000, ChronoDuration.turnEnd());
            }
        }
    }
}
