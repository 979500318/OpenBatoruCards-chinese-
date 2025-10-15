package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_G2_ScratchArtVerdantBeauty extends Card {

    public SIGNI_G2_ScratchArtVerdantBeauty()
    {
        setImageSets("WXDi-P11-074");

        setOriginalName("翠美　スクラッチアート");
        setAltNames("スイビスクラッチアート Suibi Sukuracchi Aato");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、対戦相手のトラッシュからカード１枚を対象とし、それをデッキの一番下に置く。\n" +
                "@U：このシグニがバニッシュされたとき、あなたのトラッシュから#Gを持たないシグニを３枚まで対象とし、それらを好きな順番でデッキの一番下に置く。" +
                "~#：【エナチャージ１】をする。このターン、次にあなたがルリグによってダメージを受ける場合、代わりにダメージを受けない。"
        );

        setName("en", "Scratch Art, Jade Beauty");
        setDescription("en",
                "@U: At the beginning of your attack phase, put target card from your opponent's trash on the bottom of their deck.\n" +
                "@U: When this SIGNI is vanished, put up to three target SIGNI without a #G from your trash on the bottom of your deck in any order." +
                "~#[[Ener Charge 1]]. The next time you would take damage from a LRIG this turn, instead you do not take that damage."
        );
        
        setName("en_fan", "Scratch Art, Verdant Beauty");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, target 1 card from your opponent's trash, and put it on the bottom of their deck.\n" +
                "@U: When this SIGNI is banished, target up to 3 SIGNI from your trash without #G @[Guard]@, and put them on the bottom of your deck." +
                "~#[[Ener Charge 1]]. This turn, the next time you would be damaged by a LRIG, instead you aren't damaged."
        );

		setName("zh_simplified", "翠美 刮刮画");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，从对战对手的废弃区把1张牌作为对象，将其放置到牌组最下面。\n" +
                "@U 当这只精灵被破坏时，从你的废弃区把不持有#G的精灵3张最多作为对象，将这些任意顺序放置到牌组最下面。" +
                "~#[[能量填充1]]。这个回合，下一次你因为分身受到伤害的场合，作为替代，不会受到伤害。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BEAUTIFUL_TECHNIQUE);
        setLevel(2);
        setPower(8000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff1);
            auto.setCondition(this::onAutoEff1Cond);

            registerAutoAbility(GameEventId.BANISH, this::onAutoEff2);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onAutoEff1Cond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).OP().fromTrash()).get();
            returnToDeck(target, DeckPosition.BOTTOM);
        }

        private void onAutoEff2()
        {
            DataTable<CardIndex> data = playerTargetCard(0,3, new TargetFilter(TargetHint.BOTTOM).own().SIGNI().not(new TargetFilter().withState(CardStateFlag.CAN_GUARD)).fromTrash());
            returnToDeck(data, DeckPosition.BOTTOM);
        }

        private void onLifeBurstEff()
        {
            enerCharge(1);


            blockNextDamage(cardIndexSnapshot -> CardType.isLRIG(cardIndexSnapshot.getCardReference().getType()));
        }
    }
}

