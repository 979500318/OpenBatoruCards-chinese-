package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

import java.util.List;

public final class SIGNI_G2_YuzukiMemoriaWaterPhantom extends Card {

    public SIGNI_G2_YuzukiMemoriaWaterPhantom()
    {
        setImageSets("WXDi-P11-075", "WXDi-P11-075P", "SPDi38-27");

        setOriginalName("幻水　遊月//メモリア");
        setAltNames("ゲンスイユヅキメモリア Gensui Yuzuki Memoria");
        setDescription("jp",
                "@U：のシグニがアタックしたとき、以下の２つから１つまで選ぶ。\n" +
                "$$1あなたの手札からレベル１、レベル２、レベル３のシグニを１枚ずつ公開する。そうした場合、【エナチャージ１】をする。\n" +
                "$$2あなたの手札から＜水獣＞のシグニを３枚公開する。そうした場合、カードを１枚引く。"
        );

        setName("en", "Yuzuki//Memoria, Water Phantom");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, choose up to one of the following.\n" +
                "$$1 Reveal a level one SIGNI, a level two SIGNI, and a level three SIGNI from your hand. If you do, [[Ener Charge 1]].\n" +
                "$$2 Reveal three <<Aquatic Beast>> SIGNI from your hand. If you do, draw a card."
        );
        
        setName("en_fan", "Yuzuki//Memoria, Water Phantom");
        setDescription("en_fan",
                "@U: Whenever this SIGNI attacks, @[@|choose up to 1 of the following:|@]@\n" +
                "$$1 Reveal 1 level 1, 1 level 2, and 1 level 3 SIGNI from your hand. If you do, [[Ener Charge 1]].\n" +
                "$$2 Reveal 3 <<Water Beast>> SIGNI from your hand. If you do, draw 1 card."
        );
        
		setName("zh_simplified", "幻水 游月//回忆");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，从以下的2种选1种最多。\n" +
                "$$1 从你的手牌把等级1、等级2、等级3的精灵各1张公开。这样做的场合，[[能量填充1]]。\n" +
                "$$2 从你的手牌把<<水獣>>精灵3张公开。这样做的场合，抽1张牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WATER_BEAST);
        setLevel(2);
        setPower(5000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
        }
        
        private void onAutoEff()
        {
            int mode = playerChoiceMode(0,1);
            if(mode == 1)
            {
                DataTable<CardIndex> data = playerTargetCard(0,3, new TargetFilter(TargetHint.REVEAL).own().SIGNI().withLevel(1,3).fromHand(), this::onAutoEffTargetCond);
                if(reveal(data) == 3)
                {
                    addToHand(data);
                    enerCharge(1);
                }
            } else if(mode == 2)
            {
                DataTable<CardIndex> data = playerTargetCard(0,3, new TargetFilter(TargetHint.REVEAL).own().SIGNI().withClass(CardSIGNIClass.WATER_BEAST).fromHand());
                if(reveal(data) == 3)
                {
                    addToHand(data);
                    draw(1);
                }
            }
        }
        private boolean onAutoEffTargetCond(List<CardIndex> listPickedCards)
        {
            return listPickedCards.stream().mapToDouble(c -> ((CardIndex)c).getIndexedInstance().getLevel().getValue()).distinct().count() == 3;
        }
    }
}

