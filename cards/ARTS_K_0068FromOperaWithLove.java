package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.ChronoRecordScheduler.ChronoRecord;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.CardDataImageSet.Mask;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.ARTSAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.CardAbilities;
import open.batoru.data.ability.DamageBlockParams;
import open.batoru.game.gfx.GFX;
import open.batoru.game.gfx.GFXCardTextureLayer;
import open.batoru.game.gfx.GFXTextureCardCanvas;
import open.batoru.game.gfx.GFXZoneSpriteFall;

public final class ARTS_K_0068FromOperaWithLove extends Card {

    public ARTS_K_0068FromOperaWithLove()
    {
        setImageSets(Mask.VERTICAL+"WX25-CP1-008", Mask.VERTICAL+"WX25-CP1-008U");

        setOriginalName("0068　オペラより愛をこめて！");
        setAltNames("ゼロゼロシックスティーエイトオペラヨリアイヲコメテ Zero Zero Shikkusutii Eito Opera yori Ai Wokomete");
        setDescription("jp",
                "以下の４つから２つまで選ぶ。\n" +
                "&E４枚以上@0 代わりに３つまで選ぶ。@0" +
                "$$1あなたのデッキの上からカードを５枚トラッシュに置く。この方法で＜ブルアカ＞のカードがトラッシュに置かれた場合、このターン、次にあなたがダメージを受ける場合、代わりにダメージを受けない。\n" +
                "$$2あなたのトラッシュからシグニ１枚を選び、そのカードを手札に加える。\n" +
                "$$3対戦相手のシグニ１体を対象とし、このターン、次にそれがアタックしたとき、ターン終了時まで、それのパワーをあなたのトラッシュにある＜ブルアカ＞のカード１枚につき－2000する。\n" +
                "$$4対戦相手のシグニ１体を対象とし、このターン終了時、それをバニッシュする。"
        );

        setName("en", "0068 From Opera with Love!");
        setDescription("en",
                "@[@|Choose up to 2 of the following:|@]@\n" +
                "&E4 or more@0 Instead, @[@|choose up to 3 of the following:|@]@ @0" +
                "$$1 Put the top 5 cards of your deck into the trash. If a <<Blue Archive>> card was put into the trash this way, this turn, the next time you would be damaged, instead you aren't damaged.\n" +
                "$$2 Choose 1 SIGNI from your trash, and add it to your hand.\n" +
                "$$3 Target 1 of your opponent's SIGNI, and this turn, the next time it attacks, it gets --2000 power for each <<Blue Archive>> card in your trash.\n" +
                "$$4 Target 1 of your opponent's SIGNI, and at the end of this turn, banish it."
        );

        setName("zh_simplified", "来自歌剧0068的满腔爱意！");
        setDescription("zh_simplified", 
                "从以下的4种选2种最多。\n" +
                "&E4张以上@0作为替代，选3种最多。（先选全部的选择项和对象）@0" +
                "$$1 从你的牌组上面把5张牌放置到废弃区。这个方法把<<蔚蓝档案>>牌放置到废弃区的场合，这个回合，下一次你受到伤害的场合，作为替代，不会受到伤害。\n" +
                "$$2 从你的废弃区选1张精灵，那张牌加入手牌。\n" +
                "$$3 对战对手的精灵1只作为对象，这个回合，当下一次其攻击时，直到回合结束时为止，其的力量依据你的废弃区的<<蔚蓝档案>>牌的数量，每有1张就-2000。\n" +
                "$$4 对战对手的精灵1只作为对象，这个回合结束时，将其破坏。"
        );

        setType(CardType.ARTS);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 1) + Cost.colorless(2));
        setUseTiming(UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        private final ARTSAbility arts;
        public IndexedInstance(int cardId)
        {
            super(cardId);

            arts = registerARTSAbility(this::onARTSEff);
            arts.setOnModesChosenPre(this::onARTSEffPreModeChoice);
            arts.setModeChoice(0,2);
            arts.setRecollect(4);
        }

        private void onARTSEffPreModeChoice()
        {
            arts.setModeChoice(0, arts.isRecollectFulfilled() ? 3 : 2);
        }

        private void onARTSEff()
        {
            int modes = arts.getChosenModes();

            if((modes & 1<<0) != 0)
            {
                DataTable<CardIndex> data = millDeck(5);
                
                if(data.get() != null && data.stream().anyMatch(cardIndex -> cardIndex.getIndexedInstance().getSIGNIClass().matches(CardSIGNIClass.BLUE_ARCHIVE)))
                {
                    blockNextDamage(new DamageBlockParams().withGFX("generic", new int[]{205,50,205}));
                }
            }
            if((modes & 1<<1) != 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().fromTrash()).get();
                addToHand(target);
            }
            if((modes & 1<<2) != 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().SIGNI()).get();
                
                if(target != null)
                {
                    AutoAbility attachedAuto = new AutoAbility(GameEventId.ATTACK, caller -> {
                        gainPower(caller, -2000 * new TargetFilter().own().withClass(CardSIGNIClass.BLUE_ARCHIVE).fromTrash().getValidTargetsCount(), ChronoDuration.turnEnd());
                        CardAbilities.removePlayerAbility(getAbility());
                    });
                    attachedAuto.setCondition(caller -> caller == target ? ConditionState.OK : ConditionState.BAD);
                    
                    ChronoRecord record = new ChronoRecord(target, ChronoDuration.turnEnd());
                    GFX.attachToChronoRecord(record, new GFXZoneSpriteFall(getOpponent(),target.getLocation(), "petal", 10, 20,35, 500,2500, new int[]{255,0,0}));
                    GFX.attachToChronoRecord(record, new GFXCardTextureLayer(target, new GFXTextureCardCanvas("border/minus", 0.75,3)));
                    
                    attachPlayerAbility(getOwner(), attachedAuto, record);
                }
            }
            if((modes & 1<<3) != 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
                
                if(target != null)
                {
                    int instanceId = target.getIndexedInstance().getInstanceId();
                    
                    GFX gfx = new GFXCardTextureLayer(target, new GFXTextureCardCanvas("border/banish", 0.75,3));
                    gfx.attach();
                    
                    callDelayedEffect(ChronoDuration.turnEnd(), () -> {
                        if(target.isSIGNIOnField() && target.getIndexedInstance().getInstanceId() == instanceId)
                        {
                            gfx.detach();
                            banish(target);
                        }
                    });
                }
            }
        }
    }
}
