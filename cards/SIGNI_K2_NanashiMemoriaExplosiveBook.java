package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardUnderCategory;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_K2_NanashiMemoriaExplosiveBook extends Card {

    public SIGNI_K2_NanashiMemoriaExplosiveBook()
    {
        setImageSets("WXDi-P11-081", "WXDi-P11-081P");

        setOriginalName("爆書　ナナシ//メモリア");
        setAltNames("バクショナナシメモリア Bakusho Nanashi Memoria");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、このシグニの下にカードがある場合、あなたのトラッシュから黒のシグニ１枚を対象とし、それを手札に加える。\n" +
                "@E：あなたの手札からレベル３以上のシグニ１枚をこのシグニの下に置く。" +
                "~#：対戦相手のシグニ１体を対象とし、手札を１枚捨ててもよい。そうした場合、ターン終了時まで、それのパワーを－12000する。"
        );

        setName("en", "Nanashi//Memoria, Erupting Tome");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, if there is a card underneath it, add target black SIGNI from your trash to your hand.\n" +
                "@E: You may put a level three or more SIGNI from your hand under this SIGNI." +
                "~#You may discard a card. If you do, target SIGNI on your opponent's field gets --12000 power until end of turn."
        );
        
        setName("en_fan", "Nanashi//Memoria, Explosive Book");
        setDescription("en_fan",
                "@U: Whenever this SIGNI attacks, if there is a card under this SIGNI, target 1 black SIGNI from your trash, and add it to your hand.\n" +
                "@E: You may put 1 level 3 or higher SIGNI from your hand under this SIGNI." +
                "~#Target 1 of your opponent's SIGNI, and you may discard 1 card from your hand. If you do, until end of turn, it gets --12000 power."
        );

		setName("zh_simplified", "爆书 无名//回忆");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，这只精灵的下面有牌的场合，从你的废弃区把黑色的精灵1张作为对象，将其加入手牌。\n" +
                "@E :可以从你的手牌把等级3以上的精灵1张放置到这只精灵的下面。" +
                "~#对战对手的精灵1只作为对象，可以把手牌1张舍弃。这样做的场合，直到回合结束时为止，其的力量-12000。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WEAPON);
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
            
            registerEnterAbility(this::onEnterEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onAutoEff()
        {
            if(getCardsUnderCount(CardUnderCategory.UNDER) > 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().withColor(CardColor.BLACK).fromTrash()).get();
                addToHand(target);
            }
        }
        
        private void onEnterEff()
        {
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.UNDER).own().SIGNI().withLevel(3,0).fromHand()).get();
            attach(getCardIndex(), cardIndex, CardUnderType.UNDER_GENERIC);
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

