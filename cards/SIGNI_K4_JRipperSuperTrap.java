package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_K4_JRipperSuperTrap extends Card {

    public SIGNI_K4_JRipperSuperTrap()
    {
        setImageSets("WXK01-044");

        setOriginalName("超罠　Ｊ・リッパー");
        setAltNames("チョウビンジェイリッパー Choubin Jei Rippaa");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、数字１つを宣言する。その後、あなたのデッキの一番上のカードをトラッシュに置く。それが宣言した数字と同じレベルのシグニの場合、対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－10000する。\n" +
                "@E：ターン終了時まで、レベルが偶数の対戦相手のシグニ１体のパワーを－5000する。" +
                "~#：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－12000する。"
        );

        setName("en", "J Ripper, Super Trap");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, declare a number. Then, put the top card of your deck into the trash. If it is a SIGNI with the same level as the declared number, target 1 of your opponent's SIGNI, and until end of turn, it gets --10000 power.\n" +
                "@E: Target 1 of your opponent's SIGNI with an even level, and until end of turn, it gets --5000 power." +
                "~#Target 1 of your opponent's SIGNI, and until end of turn, it gets --12000 power."
        );

		setName("zh_simplified", "超罠 J·开膛手");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，数字1种宣言。然后，你的牌组最上面的牌放置到废弃区。其是与宣言数字相同等级的精灵的场合，对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-10000。\n" +
                "@E :直到回合结束时为止，等级在偶数的对战对手的精灵1只的力量-5000。" +
                "~#对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-12000。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setLRIGType(CardLRIGType.GUZUKO);
        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClass.TRICK);
        setLevel(4);
        setPower(12000);

        setPlayFormat(PlayFormat.KEY);
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
            int number = playerChoiceNumber(0,1,2,3,4,5)-1;

            CardIndex cardIndex = millDeck(1).get();
            if(cardIndex != null && CardType.isSIGNI(cardIndex.getIndexedInstance().getTypeByRef()) && cardIndex.getIndexedInstance().getLevelByRef() == number)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
                gainPower(target, -10000, ChronoDuration.turnEnd());
            }
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI().custom(cardIndex -> cardIndex.getIndexedInstance().getLevel().getValue() % 2 == 0)).get();
            gainPower(target, -5000, ChronoDuration.turnEnd());
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -12000, ChronoDuration.turnEnd());
        }
    }
}
