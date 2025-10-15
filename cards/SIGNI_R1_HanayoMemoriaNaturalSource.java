package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AbilityCostList;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.CoinCost;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_R1_HanayoMemoriaNaturalSource extends Card {

    public SIGNI_R1_HanayoMemoriaNaturalSource()
    {
        setImageSets("WXDi-P11-059", "WXDi-P11-059P");

        setOriginalName("羅原　華代//メモリア");
        setAltNames("ラゲンハナヨメモリア Ragen Hanayo Memoria");
        setDescription("jp",
                "@U：あなたのターン終了時、手札から＜原子＞のシグニを１枚捨ててもよい。そうした場合、カードを１枚引く。その後、対戦相手のシグニを１体まで対象とし、それをバニッシュする。\n" +
                "@A $T1 %R #C #C：あなたのトラッシュから＜原子＞のシグニ１枚を対象とし、それを手札に加える。" +
                "~#：対戦相手のパワー8000以下のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Hanayo//Memoria, Natural Element");
        setDescription("en",
                "@U: At the end of your turn, you may discard an <<Atom>> SIGNI. If you do, draw a card. Then, vanish up to one target SIGNI on your opponent's field.\n" +
                "@A $T1 %R #C #C: Add target <<Atom>> SIGNI from your trash to your hand." +
                "~#Vanish target SIGNI on your opponent's field with power 8000 or less."
        );
        
        setName("en_fan", "Hanayo//Memoria, Natural Source");
        setDescription("en_fan",
                "@U: At the end of your turn, you may discard 1 <<Atom>> SIGNI from your hand. If you do, draw 1 card. Then, target up to 1 of your opponent's SIGNI, and banish it.\n" +
                "@A $T1 %R #C #C: Target 1 <<Atom>> SIGNI from your trash, and add it to your hand." +
                "~#Target 1 of your opponent's SIGNI with power 8000 or less, and banish it."
        );

		setName("zh_simplified", "罗原 华代//回忆");
        setDescription("zh_simplified", 
                "@U :你的回合结束时，可以从手牌把<<原子>>精灵1张舍弃。这样做的场合，抽1张牌。然后，对战对手的精灵1只最多作为对象，将其破坏。\n" +
                "@A $T1 %R#C #C:从你的废弃区把<<原子>>精灵1张作为对象，将其加入手牌。" +
                "~#对战对手的力量8000以下的精灵1只作为对象，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ATOM);
        setLevel(1);
        setPower(3000);

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

            ActionAbility act = registerActionAbility(new AbilityCostList(new EnerCost(Cost.color(CardColor.RED, 1)), new CoinCost(2)), this::onActionEff);
            act.setUseLimit(UseLimit.TURN, 1);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(discard(0,1, new TargetFilter().SIGNI().withClass(CardSIGNIClass.ATOM)).get() != null)
            {
                draw(1);
                
                CardIndex target = playerTargetCard(0,1, new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
                banish(target);
            }
        }
        
        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().withClass(CardSIGNIClass.ATOM).fromTrash()).get();
            addToHand(target);
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,8000)).get();
            banish(target);
        }
    }
}
