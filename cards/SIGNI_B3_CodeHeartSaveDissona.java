package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.GameLog;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_B3_CodeHeartSaveDissona extends Card {

    public SIGNI_B3_CodeHeartSaveDissona()
    {
        setImageSets("WXDi-P13-050");
        setLinkedImageSets("WXDi-P13-008");

        setOriginalName("コードハート　セイヴ//ディソナ");
        setAltNames("コードハートセイヴディソナ Koodo Haato Seibu Disona");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、このターンにあなたがスペルを使用していた場合、以下の２つから１つを選ぶ。\n" +
                "$$1カードを１枚引く。\n" +
                "$$2【エナチャージ１】\n" +
                "@E：あなたの場に《コード・ピルルク・極》がいる場合、あなたのデッキの上からカードを５枚見る。その中からスペル１枚を公開し手札に加え、残りを好きな順番でデッキの一番下に置く。" +
                "~#：カードを２枚引く。対戦相手は手札を１枚捨てる。"
        );

        setName("en", "Save//Dissona, Code: Heart");
        setDescription("en",
                "@U: At the beginning of your attack phase, if you have used a spell this turn, choose one of the following.\n$$1Draw a card. \n$$2[[Ener Charge 1]].\n@E: If \"Code Piruluk Ultimate\" is on your field, look at the top five cards of your deck. Reveal a spell from among them and add it to your hand. Put the rest on the bottom of your deck in any order." +
                "~#Draw two cards. Your opponent discards a card."
        );
        
        setName("en_fan", "Code Heart Save//Dissona");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, if you used a spell this turn, @[@|choose 1 of the following:|@]@\n" +
                "$$1 Draw 1 card.\n" +
                "$$2 [[Ener Charge 1]]\n" +
                "@E: If your LRIG is \"Code Piruluk Zenith\", look at the top 5 cards of your deck. Reveal 1 spell from among them, and add it to your hand, and put the rest on the bottom of your deck in any order." +
                "~#Draw 2 cards. Your opponent discards 1 card from their hand."
        );

		setName("zh_simplified", "爱心代号 拯救//失调");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，这个回合你把魔法使用过的场合，从以下的2种选1种。\n" +
                "$$1 抽1张牌。\n" +
                "$$2 [[能量填充1]]\n" +
                "@E :你的场上有《コード・ピルルク・極》的场合，从你的牌组上面看5张牌。从中把魔法1张公开加入手牌，剩下的任意顺序放置到牌组最下面。" +
                "~#抽2张牌。对战对手把手牌1张舍弃。\n"
        );

        setCardFlags(CardFlag.LIFEBURST | CardFlag.DISSONA);

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ELECTRIC_MACHINE);
        setLevel(3);
        setPower(10000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            registerEnterAbility(this::onEnterEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(GameLog.getTurnRecordsCount(e -> e.getId() == GameEventId.USE_SPELL && isOwnCard(e.getCaller())) > 0)
            {
                if(playerChoiceMode() == 1)
                {
                    draw(1);
                } else {
                    enerCharge(1);
                }
            }
        }
        
        private void onEnterEff()
        {
            if(getLRIG(getOwner()).getIndexedInstance().getName().getValue().contains("コード・ピルルク・極"))
            {
                look(5);
                
                CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().spell().fromLooked()).get();
                reveal(cardIndex);
                addToHand(cardIndex);
                
                while(getLookedCount() > 0)
                {
                    cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                    returnToDeck(cardIndex, DeckPosition.BOTTOM);
                }
            }
        }
        
        private void onLifeBurstEff()
        {
            draw(2);
            discard(getOpponent(), 1);
        }
    }
}
