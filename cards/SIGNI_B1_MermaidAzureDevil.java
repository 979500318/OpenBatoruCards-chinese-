package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.DataTable;

public final class SIGNI_B1_MermaidAzureDevil extends Card {

    public SIGNI_B1_MermaidAzureDevil()
    {
        setImageSets("WX24-P3-075");

        setOriginalName("蒼魔　マーメイド");
        setAltNames("ソウママーメイド Souma Meemeido");
        setDescription("jp",
                "@E：あなたのデッキの上からカードを３枚トラッシュに置く。この方法で＜悪魔＞のシグニ３枚がトラッシュに置かれた場合、カードを１枚引く。"
        );

        setName("en", "Mermaid, Azure Devil");
        setDescription("en",
                "@E: Put the top 3 cards of your deck into the trash. If 3 <<Devil>> SIGNI were put into the trash this way, draw 1 card."
        );

		setName("zh_simplified", "苍魔 人鱼");
        setDescription("zh_simplified", 
                "@E :从你的牌组上面把3张牌放置到废弃区。这个方法把<<悪魔>>精灵3张放置到废弃区的场合，抽1张牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
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
        }

        private void onEnterEff()
        {
            DataTable<CardIndex> data = millDeck(3);
            
            if(data.size() == 3 && data.stream().allMatch(c -> c.getIndexedInstance().getSIGNIClass().matches(CardSIGNIClass.DEVIL)))
            {
                draw(1);
            }
        }
    }
}
