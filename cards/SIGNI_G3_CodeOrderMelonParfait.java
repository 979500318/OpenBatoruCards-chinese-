package open.batoru.data.cards;

import open.batoru.core.Deck.DeckType;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardUnderCategory;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.ChoiceLogic;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_G3_CodeOrderMelonParfait extends Card {

    public SIGNI_G3_CodeOrderMelonParfait()
    {
        setImageSets("WX24-P2-056");

        setOriginalName("コードオーダー　メロンパフェ");
        setAltNames("コードオーダーメロンパフェ Koodo Oodaa Meron Pafe");
        setDescription("jp",
                "@C：このシグニのパワーはこのシグニの下にあるカード１枚につき＋1000される。\n" +
                "@U：あなたのアタックフェイズ開始時、このシグニの下にあるカード３枚をトラッシュに置いてもよい。そうした場合、【エナチャージ２】をする。\n" +
                "@E：あなたのトラッシュからあなたのルリグトラッシュにあるアーツ１枚につきカードを１枚まで対象とし、それらをこのシグニの下に置く。"
        );

        setName("en", "Code Order Melon Parfait");
        setDescription("en",
                "@C: This SIGNI gets +1000 power for each card under this SIGNI.\n" +
                "@U: At the beginning of your attack phase, you may put 3 cards from under this SIGNI into the trash. If you do, [[Ener Charge 2]].\n" +
                "@E: For each ARTS in your LRIG trash, target up to 1 card from your trash, and put them under this SIGNI."
        );

		setName("zh_simplified", "点单代号 甜瓜芭菲");
        setDescription("zh_simplified", 
                "@C :这只精灵的力量依据这只精灵的下面的牌的数量，每有1张就+1000。\n" +
                "@U :你的攻击阶段开始时，可以把这只精灵的下面的3张牌放置到废弃区。这样做的场合，[[能量填充2]]。\n" +
                "@E :从你的废弃区依据你的分身废弃区的必杀的数量，每有1张就把牌1张最多作为对象，将这些放置到这只精灵的下面。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.COOKING);
        setLevel(3);
        setPower(10000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerConstantAbility(new PowerModifier(this::onConstEffModGetValue));
            
            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            registerEnterAbility(this::onEnterEff);
        }
        
        private double onConstEffModGetValue(CardIndex cardIndex)
        {
            return 1000 * getCardsUnderCount(CardUnderCategory.UNDER);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            DataTable<CardIndex> data = playerTargetCard(0,3, ChoiceLogic.BOOLEAN, new TargetFilter(TargetHint.TRASH).own().under(getCardIndex()));
            
            if(trash(data) == 3)
            {
                enerCharge(2);
            }
        }
        
        private void onEnterEff()
        {
            int countARTS = new TargetFilter().own().ARTS().fromTrash(DeckType.LRIG).getValidTargetsCount();
            DataTable<CardIndex> data = playerTargetCard(0,countARTS, new TargetFilter(TargetHint.UNDER).own().fromTrash());
            
            attach(getCardIndex(), data, CardUnderType.UNDER_GENERIC);
        }
    }
}
