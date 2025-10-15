package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.stock.StockAbilityShadow;
import open.batoru.data.ability.stock.StockPlayerAbilityMikomikoBodyguard;

public final class SIGNI_B3_MikoOshiDissonaNaturalBacteriaPrincess extends Card {

    public SIGNI_B3_MikoOshiDissonaNaturalBacteriaPrincess()
    {
        setImageSets("WXDi-P12-050");
        setLinkedImageSets(Token_MikomikoBodyguard.IMAGE_SET, "WXDi-P12-008");

        setOriginalName("羅菌姫　ミコオシ//ディソナ");
        setAltNames("ラキンヒメミコオシディソナ Rakinhime Miko Oshi Disona");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたの場に《みこみこ☆さんさんまぜまぜ》がいる場合、対戦相手は【みこみこ親衛隊】１つを得る。\n" +
                "@U：このシグニがアタックしたとき、以下の２つから１つを選ぶ。\n" +
                "$$1対戦相手は手札を１枚捨てる。\n" +
                "$$2あなたの手札が対戦相手より３枚以上多い場合、次の対戦相手のターン終了時まで、このシグニは[[シャドウ（シグニ）]]を得る。" +
                "~#：対戦相手のルリグ１体を対象とし、それを凍結する。"
        );

        setName("en", "Miko Fan//Dissona, Bacteria Queen");
        setDescription("en",
                "@U: At the beginning of your attack phase, if \"Mikomiko☆Three - Three Mix - Mix\" is on your field, your opponent gains a [[Mikomiko Defense Squad]].\n@U: Whenever this SIGNI attacks, choose one of the following.\n$$1 Your opponent discards a card.\n$$2 If you have three or more cards in your hand than your opponent, this SIGNI gains [[Shadow -- SIGNI]] until the end of your opponent's next end phase." +
                "~#Freeze target LRIG on your opponent's field."
        );
        
        setName("en_fan", "Miko Oshi//Dissona, Natural Bacteria Princess");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, if your LRIG is \"Mikomiko☆Three Three Mix Mix\", your opponent gains 1 [[Mikomiko Bodyguard]].\n" +
                "@U: Whenever this SIGNI attacks, @[@|choose 1 of the following:|@]@\n" +
                "$$1 Your opponent discards 1 card from their hand.\n" +
                "$$2 If there are 3 or more cards in your hand than in your opponent's, until the end of your opponent's next turn, this SIGNI gains [[Shadow (SIGNI)]]." +
                "~#Target 1 of your opponent's LRIG, and freeze it."
        );

		setName("zh_simplified", "罗菌姬 美琴推//失调");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的场上有《みこみこ☆さんさんまぜまぜ》的场合，对战对手得到[[美琴琴亲卫队]]1个。\n" +
                "@U :当这只精灵攻击时，从以下的2种选1种。\n" +
                "$$1 对战对手把手牌1张舍弃。\n" +
                "$$2 你的手牌比对战对手多3张以上的场合，直到下一个对战对手的回合结束时为止，这只精灵得到[[暗影（精灵）]]。" +
                "~#对战对手的分身1只作为对象，将其冻结。\n"
        );

        setCardFlags(CardFlag.LIFEBURST | CardFlag.DISSONA);

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BACTERIA);
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

            AutoAbility auto1 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff1);
            auto1.setCondition(this::onAutoEff1Cond);
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff2);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onAutoEff1Cond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            if(getLRIG(getOwner()).getIndexedInstance().getName().getValue().contains("みこみこ☆さんさんまぜまぜ"))
            {
                attachPlayerAbility(getOpponent(), new StockPlayerAbilityMikomikoBodyguard(), ChronoDuration.permanent());
            }
        }
        
        private void onAutoEff2()
        {
            if(playerChoiceMode() == 1)
            {
                discard(getOpponent(), 1);
            } else {
                if((getHandCount(getOwner()) - getHandCount(getOpponent())) >= 3)
                {
                    attachAbility(getCardIndex(), new StockAbilityShadow(this::onAttachedStockEffAddCond), ChronoDuration.nextTurnEnd(getOpponent()));
                }
            }
        }
        private ConditionState onAttachedStockEffAddCond(CardIndex cardIndexSource)
        {
            return CardType.isSIGNI(cardIndexSource.getCardReference().getType()) ? ConditionState.OK : ConditionState.BAD;
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FREEZE).OP().anyLRIG()).get();
            freeze(target);
        }
    }
}
