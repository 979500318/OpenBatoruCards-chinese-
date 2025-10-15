package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardUnderCategory;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.PutUnderCost;

public final class SIGNI_B3_PelicanEelTHEDOORWaterPhantom extends Card {

    public SIGNI_B3_PelicanEelTHEDOORWaterPhantom()
    {
        setImageSets("WXDi-P15-050");
        setLinkedImageSets("WXDi-P15-007");

        setOriginalName("幻水姫　フクロウナギ//THE DOOR");
        setAltNames("ゲンスイヒメフクロウナギザドアー Gensuihime Fukurounagi Za Doaa");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたの場に《解放者エルドラ×マークν》がいる場合、以下の２つから１つを選ぶ。\n" +
                "$$1対戦相手は手札を１枚捨てる。\n" +
                "$$2あなたの場にあるシグニの下にカードが合計２枚以上ある場合、対戦相手の手札を１枚見ないで選び、捨てさせる。\n" +
                "@E @[手札から＜解放派＞のシグニ１枚をこのシグニの下に置く]@：カードを１枚引く。"
        );

        setName("en", "Eel//THE DOOR, Phantom Aquatic Beast");
        setDescription("en",
                "@U: At the beginning of your attack phase, if \"Liberator Eldora × Mark ν\" is on your field, choose one of the following.\n$$1Your opponent discards a card.\n$$2If there are a total of two or more cards underneath SIGNI on your field, your opponent discards a card at random.\n@E @[Put a <<Liberation Division>> SIGNI from your hand under this SIGNI]@: Draw a card."
        );
        
        setName("en_fan", "Pelican Eel//THE DOOR, Water Phantom");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, if your LRIG is \"Liberator Eldora×Mark Nu\", @[@|choose 1 of the following:|@]@\n" +
                "$$1 Your opponent discards 1 card from their hand.\n" +
                "$$2 If there are a total of 2 or more cards under your SIGNI on the field, choose 1 card from your opponent's hand without looking, and your opponent discards it.\n" +
                "@E @[Put 1 <<Liberation Faction>> SIGNI from your hand under this SIGNI]@: Draw 1 card."
        );

		setName("zh_simplified", "幻水姬 宽咽鱼//THE DOOR");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的场上有《解放者エルドラ×マークν》的场合，从以下的2种选1种。\n" +
                "$$1 对战对手把手牌1张舍弃。\n" +
                "$$2 你的场上的精灵有下面的牌在合计2张以上的场合，不看对战对手的手牌选1张，舍弃。\n" +
                "@E 从手牌把<<解放派>>精灵1张放置到这只精灵的下面:抽1张牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.LIBERATION_FACTION,CardSIGNIClass.WATER_BEAST);
        setLevel(3);
        setPower(12000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
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
            
            registerEnterAbility(new PutUnderCost(new TargetFilter().SIGNI().withClass(CardSIGNIClass.LIBERATION_FACTION).fromHand()), this::onEnterEff);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(getLRIG(getOwner()).getIndexedInstance().getName().getValue().contains("解放者エルドラ×マークν"))
            {
                if(playerChoiceMode() == 1)
                {
                    discard(getOpponent(), 1);
                } else if(new TargetFilter().own().SIGNI().withUnderType(CardUnderCategory.UNDER).getValidTargetsCount() >= 2) {
                    CardIndex cardIndex = playerChoiceHand().get();
                    discard(cardIndex);
                }
            }
        }
        
        private void onEnterEff()
        {
            draw(1);
        }
    }
}
